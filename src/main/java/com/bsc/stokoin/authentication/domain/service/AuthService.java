package com.bsc.stokoin.authentication.domain.service;

import com.bsc.stokoin.authentication.domain.LogoutAccessToken;
import com.bsc.stokoin.authentication.domain.LogoutRefreshToken;
import com.bsc.stokoin.authentication.domain.RefreshToken;
import com.bsc.stokoin.authentication.domain.repository.TokenRepository;
import com.bsc.stokoin.authentication.domain.service.dto.AuthResponseDto;
import com.bsc.stokoin.common.exception.security.NotExistsRefreshTokenException;
import com.bsc.stokoin.config.security.dto.OAuthAttributes;
import com.bsc.stokoin.config.security.dto.OauthInfoAppResponse;
import com.bsc.stokoin.config.security.dto.OauthInfoResponse;
import com.bsc.stokoin.config.security.dto.OauthTokenResponse;
import com.bsc.stokoin.config.security.dto.request.OauthInfoAppRequest;
import com.bsc.stokoin.user.domain.User;
import com.bsc.stokoin.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService implements AuthCommandUseCase {

    private final UserRepository userRepository;
    private final Environment environment;

    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final TokenRepository tokenRepository;
    private final InMemoryClientRegistrationRepository inMemoryClientRegistrationRepository;

    private final AuthenticationManager authenticationManager;

    @Override
    public void logout(String accessToken, String refreshToken) {
        saveLogoutAccessToken(accessToken);
        saveLogoutRefreshToken(refreshToken);
    }

    private void saveLogoutAccessToken(String accessToken) {
        String removedTypeAccessToken = getRemovedBearerType(accessToken);
        LogoutAccessToken logoutAccessToken = LogoutAccessToken.of(removedTypeAccessToken,
                getRemainingMilliSecondsFromToken(removedTypeAccessToken));
        tokenRepository.saveLogoutAccessToken(logoutAccessToken);
    }

    private void saveLogoutRefreshToken(String refreshToken) {
        String removedTypeRefreshToken = getRemovedBearerType(refreshToken);
        LogoutRefreshToken logoutRefreshToken = LogoutRefreshToken.of(removedTypeRefreshToken,
                getRemainingMilliSecondsFromToken(removedTypeRefreshToken));
        tokenRepository.saveLogoutRefreshToken(logoutRefreshToken);
    }

    private String getRemovedBearerType(String token){
        return token.substring(7);
    }

    @Override
    public AuthResponseDto reissue(String refreshToken) {
        refreshToken = tokenProvider.removeType(refreshToken);
        isInRedisOrThrow(refreshToken);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String newAccessToken = tokenProvider.createAccessToken(authentication);
        if (tokenProvider.isMoreThanReissueTime(refreshToken))
            return AuthResponseDto.of(newAccessToken, refreshToken);

        deleteOriginRefreshToken(refreshToken);
        String newRefreshToken = createNewRefreshToken(authentication);
        return AuthResponseDto.of(newAccessToken, newRefreshToken);
    }

    @Override
    public OauthInfoResponse oauth2Authorization(String code, String providerName, String state, String redirectUri) {

        //todo : Exception 처리 필요
        ClientRegistration provider = inMemoryClientRegistrationRepository.findByRegistrationId(providerName);
        String registrationId = provider.getRegistrationId();
        String userNameAttributeName = provider.getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OauthTokenResponse oauthTokenResponse = getToken(code, provider, redirectUri,state);
        Map<String, Object> oAuth2User = getUserInfo(oauthTokenResponse, provider);
        OAuthAttributes attributes = OAuthAttributes.of(registrationId,userNameAttributeName,oAuth2User);
        User user = saveOrUpdate(attributes);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getProviderId(), user.getEmail()+user.getProviderId());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = getAccessToken(authentication);
        String refreshToken = getRefreshToken(authentication);
        tokenRepository.saveRefreshToken(
                RefreshToken.of(refreshToken,getRemainingMilliSecondsFromToken(refreshToken)));

        return OauthInfoResponse.from(user,accessToken,refreshToken);
    }

    @Override
    public OauthInfoAppResponse oauth2AppAuthorization(OauthInfoAppRequest oauthInfoAppRequest) {

        User user = saveOrUpdate(oauthInfoAppRequest);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getProviderId(), user.getEmail()+user.getProviderId());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = getAccessToken(authentication);
        String refreshToken = getRefreshToken(authentication);

        return OauthInfoAppResponse.from(accessToken,refreshToken);
    }
    private String getRefreshToken(Authentication authentication) {
        return tokenProvider.createRefreshToken(authentication);
    }

    private String getAccessToken(Authentication authentication) {
        return tokenProvider.createAccessToken(authentication);
    }

    //TODO : 따로 클래스로 분리
    private Map<String, Object> getUserInfo(OauthTokenResponse oauthTokenResponse, ClientRegistration provider) {
        try {
            return WebClient.create()
                    .get()
                    .uri(provider.getProviderDetails().getUserInfoEndpoint().getUri())
                    .headers(header -> header.setBearerAuth(oauthTokenResponse.getAccess_token()))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                    .block();
        } catch (WebClientResponseException e){
            if (e.getStatusCode().is4xxClientError()){
//                throw new RestException(HttpStatus.BAD_REQUEST, "카카오 API 호출 중 오류가 발생했습니다. access token 을 확인해주세요. access token = " + oauthTokenResponse.getAccess_token());
            } else if (e.getStatusCode().is5xxServerError()) {
//                throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "카카오 API 서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
            }
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
    //TODO : 따로 클래스로 분리
    private OauthTokenResponse getToken(String code, ClientRegistration provider, String redirectUri, String state) {
        try {
            return
                    WebClient.create()
                            .post()
                            .uri(provider.getProviderDetails().getTokenUri())
                            .headers(header -> {
                                header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                                header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                            })
                            .bodyValue(tokenRequest(code, provider, redirectUri, state))
                            .retrieve()
                            .bodyToMono(OauthTokenResponse.class)
                            .block();
        } catch (WebClientResponseException e){
            e.printStackTrace();
            HttpStatusCode statusCode = e.getStatusCode();
            String message = "";
            if (BAD_REQUEST.equals(statusCode)) {
                message = "인가코드를 확인해주세요. (Authorization_Code = " + code + ")";
                log.error(message);
            } else if (UNAUTHORIZED.equals(statusCode)) {
                message = "클라이언트 아이디 또는 시크릿 키를 확인해주세요.";
                log.error(message);
            } else if (FORBIDDEN.equals(statusCode)) {
                message = "해당 애플리케이션에 대한 사용자 인증이 실패했습니다.";
                log.error(message);
            } else if (INTERNAL_SERVER_ERROR.equals(statusCode)) {
                message = "서버 내부 오류가 발생했습니다.";
                log.error(message);
            } else {
                message = "API 호출 중 오류가 발생했습니다.";
                log.error(message);
            }
            e.printStackTrace();
            throw new RuntimeException(message);
        }
    }

    private MultiValueMap<String, String> tokenRequest(String code, ClientRegistration provider, String redirectUri,String state) {

        log.info("redirectUri {}",redirectUri);
        MultiValueMap<String ,String> formData = new LinkedMultiValueMap<>();
        formData.add("code",code);
        formData.add("grant_type","authorization_code");
        if (redirectUri == null){
            formData.add("redirect_uri", "http://localhost:8080/api/v1/oauth2/authorization");
        }
        formData.add("redirect_uri", redirectUri);
        formData.add("client_secret",provider.getClientSecret());
        formData.add("client_id", provider.getClientId());
        formData.add("state", state);

        return formData;
    }

    private User saveOrUpdate(OAuthAttributes attributes) {

        User user;
        Optional<User> userOptional = userRepository.findByEmail(attributes.getEmail());
        if (userOptional.isPresent()) {
            user = userOptional.get();
            user.updateBySocialProfile(attributes.getName(),attributes.getPicture());
        }
        else{
            String encodedPassword = passwordEncoder.encode(attributes.getEmail() + attributes.getProviderId());
            user = userRepository.save(attributes.toUserEntity(encodedPassword));
        }
        return user;
    }
    private User saveOrUpdate(OauthInfoAppRequest oauthInfoAppRequest) {

        User user;
        Optional<User> userOptional = userRepository.findByEmail(oauthInfoAppRequest.getEmail());
        if (userOptional.isPresent()) {
            user = userOptional.get();
            user.updateBySocialProfile(oauthInfoAppRequest.getName(),oauthInfoAppRequest.getPicture());
        }
        else{
            String encodedPassword = passwordEncoder.encode(oauthInfoAppRequest.getEmail() + oauthInfoAppRequest.getProviderId());
            user = userRepository.save(oauthInfoAppRequest.toUserEntity(encodedPassword));
        }
        return user;
    }

    private void isInRedisOrThrow(String refreshToken) {
        if (!tokenRepository.existsRefreshTokenById(refreshToken)){
            throw new NotExistsRefreshTokenException();
        }
    }

    private void deleteOriginRefreshToken(String refreshToken) {
        tokenRepository.deleteRefreshTokenById(refreshToken);
        tokenRepository.saveLogoutRefreshToken(
                LogoutRefreshToken.of(refreshToken,getRemainingMilliSecondsFromToken(refreshToken)));
    }

    private String createNewRefreshToken(Authentication authentication) {
        String newRefreshToken = tokenProvider.createRefreshToken(authentication);
        tokenRepository.saveRefreshToken(
                RefreshToken.of(newRefreshToken,getRemainingMilliSecondsFromToken(newRefreshToken)));
        return newRefreshToken;
    }

    private long getRemainingMilliSecondsFromToken(String token) {
        return tokenProvider.getRemainingMilliSecondsFromToken(token);
    }
}
