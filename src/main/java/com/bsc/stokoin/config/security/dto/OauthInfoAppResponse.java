package com.bsc.stokoin.config.security.dto;


import com.bsc.stokoin.user.domain.enums.AuthProvider;
import com.bsc.stokoin.user.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OauthInfoAppResponse {

    private String accessToken;
    private String refreshToken;

    public static OauthInfoAppResponse from(String accessToken, String refreshToken) {
        return OauthInfoAppResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
