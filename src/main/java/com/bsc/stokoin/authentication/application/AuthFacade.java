package com.bsc.stokoin.authentication.application;

import com.bsc.stokoin.authentication.domain.service.AuthCommandUseCase;
import com.bsc.stokoin.authentication.domain.service.dto.AuthResponseDto;
import com.bsc.stokoin.config.security.dto.OauthInfoAppResponse;
import com.bsc.stokoin.config.security.dto.OauthInfoResponse;
import com.bsc.stokoin.config.security.dto.request.OauthInfoAppRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthFacade {

    private final AuthCommandUseCase authCommandUseCase;

    public void logout(String accessToken, String refreshToken) {
        authCommandUseCase.logout(accessToken,refreshToken);
    }

    public AuthResponseDto reissue(String refreshToken) {
        return authCommandUseCase.reissue(refreshToken);
    }

    public OauthInfoResponse oauth2Authorization(String code, String providerName, String state, String redirect_uri) {
        return authCommandUseCase.oauth2Authorization(code,providerName,state,redirect_uri);
    }

    public OauthInfoAppResponse oauth2AppAuthorization(OauthInfoAppRequest oauthInfoAppRequest) {
        return authCommandUseCase.oauth2AppAuthorization(oauthInfoAppRequest);
    }
}
