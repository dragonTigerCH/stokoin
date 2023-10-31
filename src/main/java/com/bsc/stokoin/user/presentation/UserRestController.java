package com.bsc.stokoin.user.presentation;


import com.bsc.stokoin.common.CommonResponse;
import com.bsc.stokoin.common.LoginUser;
import com.bsc.stokoin.common.exception.ValidatedException;
import com.bsc.stokoin.user.application.UserFacade;
import com.bsc.stokoin.user.domain.User;
import com.bsc.stokoin.user.presentation.dto.request.UserSimpleInfoRequest;
import com.bsc.stokoin.user.presentation.dto.response.UserSimpleInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Tag(name = "유저 API")
@RestController
@RequestMapping("/users")
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class UserRestController {

    private final UserFacade userFacade;

    @Operation(summary = "유저 정보 조회 API", description = "<br>" +
            "유저 정보 조회 API<br>" +
            "<b>*필터에서 엑세스 토큰에 있는 페이로드에 Oauth2 시퀀스값(ProviderId)으로 유저를 조회 한다. *<b><br>" +
            "유저정보가 있다면 유저 정보를 반환 하고 없다면 오류를 반환 한다.<br>")
    @GetMapping
    public ResponseEntity<CommonResponse<UserSimpleInfoResponse>> getUserSimpleInfo(@LoginUser User user){
        return ResponseEntity.ok(CommonResponse.success(UserSimpleInfoResponse.from(user)));
    }

    @Operation(summary = "유저 정보 수정 API", description = "<br>" +
            "유저 정보 수정 API<br>" +
            "유저 정보가 수정되면 isInitProfile 필드는 true 로 변경된다" +
            "유저 정보가 수정되면 result : SUCCESS 메시지를 반환 한다.")
    @PutMapping
    public ResponseEntity<CommonResponse> updateUserSimpleInfo(@LoginUser User user,
                                                               @Validated @RequestBody UserSimpleInfoRequest userSimpleInfoRequest,
                                                               BindingResult errors){
        if (errors.hasErrors()){
            throw new ValidatedException(errors);
        }
        userFacade.updateUserSimpleInfo(userSimpleInfoRequest,user.getId());
        return ResponseEntity.ok(CommonResponse.success());
    }


}
