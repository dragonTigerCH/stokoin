package com.bsc.stokoin.authentication.presentation;

import com.bsc.stokoin.authentication.application.AuthFacade;
import com.bsc.stokoin.authentication.presentation.dto.AuthResponse;
import com.bsc.stokoin.common.CommonResponse;
import com.bsc.stokoin.common.exception.ValidatedException;
import com.bsc.stokoin.config.security.dto.OauthInfoResponse;
import com.bsc.stokoin.config.security.dto.OauthInfoAppResponse;
import com.bsc.stokoin.config.security.dto.request.OauthInfoAppRequest;
import com.bsc.stokoin.user.domain.enums.AuthProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

//TODO: Redis
@Tag(name = "인증 API")
@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthRestController {

    private final AuthFacade authFacade;

    @PostMapping("/logout")
    public ResponseEntity<CommonResponse> logout(@RequestHeader(value = "Authorization") String accessToken,
                                                 @RequestHeader(value = "refreshToken") String refreshToken) {
        authFacade.logout(accessToken, refreshToken);
        return ResponseEntity.ok(CommonResponse.success());
    }

    @Operation(summary = "AccessToken & RefreshToken 재발급 API",
        description = "AccessToken & RefreshToken 재발급 API<br>" +
        "발급한 리프레시 토큰의 만료기간이 3일 이내로 남았을경우에만 재발급을 하고<br>" +
        "리프레시 토큰의 만료기간이 3일 이상일 경우 엑세스 토큰만 재발급 된다<br>" +
        "accessToken : 만료 기간 6시간<br>" +
        "refreshToken : 만료 기간 7일<br>" +
        "accessToken : 재발급 가능<br>" +
        "refreshToken : 만료 기간이 3일 이내로 남았을 경우 재발급 가능")
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/reissue")
    public ResponseEntity<CommonResponse<AuthResponse>> reissueToken(@RequestHeader(value = "Authorization") String refreshToken) {
        AuthResponse authResponse = AuthResponse.from(authFacade.reissue(refreshToken));
        return ResponseEntity.ok(CommonResponse.success(authResponse));
    }

    @Operation(summary = "OAuth2.0 인증코드를 받아서 AccessToken & RefreshToken을 받는 API",
            description = "OAuth2.0 인증코드를 받아서 AccessToken & RefreshToken 발급 받는 API<br>" +
                    "인증코드(AuthorizationCode)를 받아서 AuthProvider Type 으로 구분 (google, naver, kakao)<br>" +
                    "redirect_uri : 인증 후 유저정보를 리다이렉트 할 URI<br>" +
                    "state : CSRF 공격 방지를 위한 상태값<br><br>" +
                    "현재 소셜 로그인시 기존 회원가입이 되어있는 유저라면 Name && picture 정보를 업데이트 합니다.<br>" +
                    "현재 소셜 로그인시 기존 회원가입이 되어있지 않은 유저라면 회원가입을 진행합니다.")
    @GetMapping("/oauth2/authorization")
    public ResponseEntity<CommonResponse<OauthInfoResponse>> oauth2Authorization(
            @RequestParam String code,
            @RequestParam(required = false) AuthProvider authProvider,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String redirect_uri) {

        //todo:로컬에서 테스트를 위한 코드
        String testAuthProvier = authProvider == null ? "naver" : authProvider.name();

        return ResponseEntity.ok(CommonResponse.success(authFacade.oauth2Authorization(code, authProvider.name(), state, redirect_uri)));
    }

    @Operation(summary = "[APP VERSION] OAuth2.0 로그인 회원정보와 부가적인 유저정보 저장 및 로그인 API",
            description = "OAuth2.0 로그인 회원정보와 부가적인 유저정보 저장 및 로그인 API<br>" +
                    "providerId 로 현재 가입된 회원인지 아닌지를 판별하며,<br>" +
                    "회원가입이 되어있지않다면 회원정보와 부가적인 유저정보를 저장하고 로그인을 진행합니다.<br>" +
                    "회원가입이 되어있다면 로그인을 진행합니다.<br>")
    @PostMapping("/oauth2/authorization/app")
    public ResponseEntity<CommonResponse<OauthInfoAppResponse>> oauth2AuthorizationApp(
            @Validated @RequestBody OauthInfoAppRequest oauthInfoAppRequest,
            BindingResult errors) {

            if (errors.hasErrors()){
                throw new ValidatedException(errors);
            }
            return ResponseEntity.ok(CommonResponse.success(authFacade.oauth2AppAuthorization(oauthInfoAppRequest)));
        }

    }
