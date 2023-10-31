package com.bsc.stokoin.authentication.domain.repository;


import com.bsc.stokoin.authentication.domain.LogoutAccessToken;
import com.bsc.stokoin.authentication.domain.LogoutRefreshToken;
import com.bsc.stokoin.authentication.domain.RefreshToken;

public interface TokenRepository {

    void saveLogoutAccessToken(LogoutAccessToken logoutAccessToken);

    void saveLogoutRefreshToken(LogoutRefreshToken logoutRefreshToken);

    void saveRefreshToken(RefreshToken refreshToken);

    boolean existsLogoutAccessTokenById(String token);

    boolean existsLogoutRefreshTokenById(String token);

    boolean existsRefreshTokenById(String token);

    void deleteRefreshTokenById(String token);

}
