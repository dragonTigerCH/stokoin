package com.bsc.stokoin.config.security.dto.request;

import com.bsc.stokoin.user.domain.User;
import com.bsc.stokoin.user.domain.enums.AuthProvider;
import com.bsc.stokoin.user.domain.enums.Gender;
import com.bsc.stokoin.user.domain.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OauthInfoAppRequest {


    @NotBlank(message = "이메일을 입력하세요.")
    private String email;
    @NotBlank(message = "이름을 입력하세요.")
    private String name;
    @NotBlank(message = "사진을 입력하세요.")
    private String picture;
    @NotBlank(message = "닉네임을 입력하세요.")
    private String nickname;
    @NotNull(message = "성별을 입력하세요.")
    private Gender gender;
    @NotBlank(message = "생일을 입력하세요.")
    private String birthday;
    @NotNull(message = "인증 제공자를 입력하세요.")
    private AuthProvider authProvider;
    @NotBlank(message = "인증 제공자 ID를 입력하세요.")
    private String providerId;


    public User toUserEntity(String encodedPassword) {
        return User.builder()
                .name(name)
                .email(email)
                .nickname(nickname)
                .gender(gender)
                .birthday(birthday)
                .picture(picture)
                .isInitProfile(true)
                .password(encodedPassword)
                .role(Role.USER)
                .providerId(providerId)
                .authProvider(authProvider)
                .build();
    }
}
