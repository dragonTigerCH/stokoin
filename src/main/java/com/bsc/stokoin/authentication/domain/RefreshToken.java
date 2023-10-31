package com.bsc.stokoin.authentication.domain;


import org.springframework.data.redis.core.RedisHash;



@RedisHash("refreshToken") // Redis에 저장될 객체임을 명시 (key, value) 형태로 저장됨 (key: refreshToken, value: RefreshToken)
public class RefreshToken extends Token {

    private RefreshToken(String id, long expiration) {
        super(id, expiration);
    }

    public static RefreshToken of (String refreshToken, Long expiration){
        return new RefreshToken(refreshToken,expiration);
    }
}
