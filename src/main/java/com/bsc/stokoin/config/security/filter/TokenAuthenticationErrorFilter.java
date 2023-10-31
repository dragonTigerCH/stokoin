package com.bsc.stokoin.config.security.filter;

import com.bsc.stokoin.common.exception.security.TokenAuthenticationFilterException;
import com.bsc.stokoin.config.security.SendErrorUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenAuthenticationErrorFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (RedisConnectionFailureException e){
            log.error("[TokenAuthenticationErrorFilter error] {}",e.getMessage(),e);
            SendErrorUtil.sendServerErrorResponse(response,objectMapper);
        }
        catch (TokenAuthenticationFilterException e) {
            log.error("[TokenAuthenticationErrorFilter error] {}",e.getMessage(),e);
            SendErrorUtil.sendUnauthorizedErrorResponse(response,objectMapper);
        }
    }

}
