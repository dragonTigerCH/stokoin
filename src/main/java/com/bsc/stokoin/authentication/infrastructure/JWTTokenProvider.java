package com.bsc.stokoin.authentication.infrastructure;

import com.bsc.stokoin.authentication.domain.service.TokenProvider;
import com.bsc.stokoin.config.security.dto.PrincipalDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JWTTokenProvider implements TokenProvider {

    public static final String TOKEN_TYPE = "Bearer ";

    private final String secretKey;
    private final long accessTokenExpirationTimeInMilliSeconds;
    private final long refreshTokenExpirationTimeInMilliSeconds;
    private final long reissueRefreshTokenTimeInMilliSeconds;

    public JWTTokenProvider(
            @Value("${jwt.secret-key}") String secretKey,
            @Value("${jwt.access-expiration-time}") long accessTokenExpirationTimeInMilliSeconds,
            @Value("${jwt.refresh-expiration-time}") long refreshTokenExpirationTimeInMilliSeconds,
            @Value("${jwt.reissue-refresh-time}") long reissueRefreshTokenTimeInMilliSeconds
            ) {
        this.secretKey = secretKey;
        this.accessTokenExpirationTimeInMilliSeconds = accessTokenExpirationTimeInMilliSeconds;
        this.refreshTokenExpirationTimeInMilliSeconds = refreshTokenExpirationTimeInMilliSeconds;
        this.reissueRefreshTokenTimeInMilliSeconds = reissueRefreshTokenTimeInMilliSeconds;
    }

    public String createAccessToken(Authentication authentication) {
        return createToken(authentication, accessTokenExpirationTimeInMilliSeconds);
    }

    public String createRefreshToken(Authentication authentication){
        return createToken(authentication, refreshTokenExpirationTimeInMilliSeconds);
    }

    @Override
    public String createTempAccessToken(String email) {
        return createTempToken(email, accessTokenExpirationTimeInMilliSeconds);
    }
    @Override
    public String createTempRefreshToken(String email) {
        return createTempToken(email, refreshTokenExpirationTimeInMilliSeconds);
    }

    public String createTempToken(String email, long expirationTimeInMilliSeconds) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTimeInMilliSeconds);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
//java.lang.ClassNotFoundException: javax.xml.bind.DatatypeConverter

    private String createToken(Authentication authentication, long accessTokenExpirationTimeInMilliSeconds) {
//        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + accessTokenExpirationTimeInMilliSeconds);

        return Jwts.builder()
                .setSubject(principalDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public String getUserProviderIdFromToken(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    public long getRemainingMilliSecondsFromToken(String token){
        Date expiration = getClaims(token).getExpiration();
        return expiration.getTime() - (new Date()).getTime();
    }

    public boolean isMoreThanReissueTime(String token){
        return getRemainingMilliSecondsFromToken(token) >= reissueRefreshTokenTimeInMilliSeconds; // 토큰 재발급 시간 이상이면 true 반환
    } // 가져온 토큰시간 >= 3일

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken); // 토큰이 유효한지 확인 무엇을보고 판단하는거야? secretKey 를 보고 판단해
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String removeType(String token){
        return token.substring(TOKEN_TYPE.length());
    }

//    private Key getHmacShaKeyForKey() {
//        // jwtSecret 알고리즘
//        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
//        Key key = Keys.hmacShaKeyFor(keyBytes);
//        return key;
//    }
}
