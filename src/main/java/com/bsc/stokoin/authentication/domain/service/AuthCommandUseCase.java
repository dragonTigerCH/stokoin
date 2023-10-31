package com.bsc.stokoin.authentication.domain.service;


import com.bsc.stokoin.authentication.domain.service.dto.AuthResponseDto;
import com.bsc.stokoin.config.security.dto.OauthInfoAppResponse;
import com.bsc.stokoin.config.security.dto.OauthInfoResponse;
import com.bsc.stokoin.config.security.dto.request.OauthInfoAppRequest;

public interface AuthCommandUseCase {

    void logout(String accessToken, String refreshToken);

    AuthResponseDto reissue(String refreshToken);

    OauthInfoResponse oauth2Authorization(String code, String providerName, String state, String redirect_uri);

    OauthInfoAppResponse oauth2AppAuthorization(OauthInfoAppRequest oauthInfoAppRequest);
}
