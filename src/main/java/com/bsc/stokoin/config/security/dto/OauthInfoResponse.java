package com.bsc.stokoin.config.security.dto;

import com.bsc.stokoin.user.domain.User;
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
public class OauthInfoResponse {

    private Long userId;
    private String email;
    private String name;
    private String picture;
    private Boolean isInitProfile;
    private String nickname;
    private String gender;
    private String birthday;
    private Role role;
    private AuthProvider authProvider;
    private String providerId;
    private String accessToken;
    private String refreshToken;

    public static OauthInfoResponse from(User user, String accessToken, String refreshToken) {
        return OauthInfoResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .isInitProfile(user.getIsInitProfile())
                .nickname(user.getNickname())
                .picture(user.getPicture())
                .role(user.getRole())
                .authProvider(user.getAuthProvider())
                .providerId(user.getProviderId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
