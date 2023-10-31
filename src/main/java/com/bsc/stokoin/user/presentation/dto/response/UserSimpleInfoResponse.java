package com.bsc.stokoin.user.presentation.dto.response;

import com.bsc.stokoin.user.domain.User;
import com.bsc.stokoin.user.domain.enums.AuthProvider;
import com.bsc.stokoin.user.domain.enums.Gender;
import com.bsc.stokoin.user.domain.enums.Role;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserSimpleInfoResponse {

    private Long userId;
    private String email;
    private Boolean isInitProfile;
    private String name;
    private String picture;
    private String providerId;
    private String nickname;
    private String birthday;
    private Gender gender;
    private AuthProvider authProvider;
    private Role role;


    public static UserSimpleInfoResponse from(User user) {
        return UserSimpleInfoResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .isInitProfile(user.getIsInitProfile())
                .name(user.getName())
                .picture(user.getPicture())
                .providerId(user.getProviderId())
                .nickname(user.getNickname())
                .birthday(user.getBirthday())
                .gender(user.getGender())
                .authProvider(user.getAuthProvider())
                .role(user.getRole())
                .build();
    }
}
