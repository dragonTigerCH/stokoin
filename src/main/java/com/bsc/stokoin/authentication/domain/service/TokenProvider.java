package com.bsc.stokoin.authentication.domain.service;

import org.springframework.security.core.Authentication;

public interface TokenProvider {

    String TOKEN_TYPE = "Bearer ";

    String createAccessToken(Authentication authentication);

    String createRefreshToken(Authentication authentication);
    String createTempAccessToken(String email);
    String createTempRefreshToken(String email);

    String getUserProviderIdFromToken(String token);

    long getRemainingMilliSecondsFromToken(String token);

    boolean isMoreThanReissueTime(String token);

    boolean validateToken(String authToken);

    String removeType(String token);
}
