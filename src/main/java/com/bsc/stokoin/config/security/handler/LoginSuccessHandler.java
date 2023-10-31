package com.bsc.stokoin.config.security.handler;

import com.bsc.stokoin.authentication.domain.RefreshToken;
import com.bsc.stokoin.authentication.domain.repository.TokenRepository;
import com.bsc.stokoin.authentication.domain.service.TokenProvider;
import com.bsc.stokoin.authentication.presentation.dto.AuthResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final ObjectMapper objectMapper;
    private final TokenRepository tokenRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        log.info("Login Success");
        String refreshToken = tokenProvider.createRefreshToken(authentication);
        String accessToken = tokenProvider.createAccessToken(authentication);
        AuthResponse authResponse = AuthResponse.of(accessToken,refreshToken);
        tokenRepository
                .saveRefreshToken(RefreshToken.of(refreshToken, tokenProvider.getRemainingMilliSecondsFromToken(refreshToken)));

        objectMapper.writeValue(response.getWriter(), authResponse);
    }
}
