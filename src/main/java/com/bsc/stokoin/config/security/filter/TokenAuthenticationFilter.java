package com.bsc.stokoin.config.security.filter;

import com.bsc.stokoin.authentication.domain.repository.TokenRepository;
import com.bsc.stokoin.authentication.domain.service.TokenProvider;
import com.bsc.stokoin.common.exception.security.TokenAuthenticationFilterException;
import com.bsc.stokoin.common.exception.user.UserNotFoundException;
import com.bsc.stokoin.config.security.dto.PrincipalDetails;
import com.bsc.stokoin.user.domain.User;
import com.bsc.stokoin.user.domain.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);
            if (isValidToken(jwt)) {
                log.info("isValidToken jwt : {}", jwt);
                String providerId = tokenProvider.getUserProviderIdFromToken(jwt);
                User user = userRepository.findByProviderId(providerId).orElseThrow(() -> new UserNotFoundException());
                PrincipalDetails principalDetails = PrincipalDetails.from(user);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (RedisConnectionFailureException e) {
            throw new RedisConnectionFailureException("redis 커넥션에 실패했습니다.");
        } catch(Exception e){
            throw new TokenAuthenticationFilterException(e);
        }

        filterChain.doFilter(request, response);

    }

    private boolean isValidToken(String jwt) {
        return StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt);
//                && !tokenRepository.existsLogoutAccessTokenById(jwt) && !tokenRepository.existsLogoutRefreshTokenById(jwt);

    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TokenProvider.TOKEN_TYPE)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
