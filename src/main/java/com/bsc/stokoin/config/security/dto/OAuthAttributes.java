package com.bsc.stokoin.config.security.dto;

import com.bsc.stokoin.common.exception.security.NotSupportRegistrationIdException;
import com.bsc.stokoin.user.domain.User;
import com.bsc.stokoin.user.domain.enums.AuthProvider;
import com.bsc.stokoin.user.domain.enums.Gender;
import com.bsc.stokoin.user.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@ToString
@Slf4j
public class OAuthAttributes {

    private Map<String,Object> attributes;
    private String nameAttributeKey;
    private String email;
    private String password;
    private String name;
    private Gender gender;
    private String birthday;
    private String nickname;
    private Boolean isInitProfile;
    private String picture;
    private AuthProvider authProvider;
    private String providerId;


    public static OAuthAttributes of(String registrationId,
                                     String userNameAttributeName,
                                     Map<String,Object> attributes){

        switch (registrationId){
            case "google":
                return ofGoogle(userNameAttributeName,attributes);
            case "naver":
                return ofNaver("id",attributes);
            case "kakao":
                return ofKakao("id",attributes);
            default:
                throw new NotSupportRegistrationIdException();
        }
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String,Object> attributes){
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .providerId((String) attributes.get("sub")) //todo 확인필요
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .authProvider(AuthProvider.google)
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String,Object> attributes) {
        Map<String,Object> response = (Map<String,Object>) attributes.get("response");
        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .providerId((String) response.get("id"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .authProvider(AuthProvider.naver)
                .build();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String,Object> attributes) {
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");


        return OAuthAttributes.builder()
//                .name((String) profile.get("nickname")) //TODO: 현재 NAME 검수 안됨
                .name("카카오 익명의 유저")
                .email((String) account.get("email"))
                .picture((String) profile.get("profile_image_url"))
                .providerId(String.valueOf(attributes.get("id")))
                .attributes(account)
                .nameAttributeKey(userNameAttributeName)
                .authProvider(AuthProvider.kakao)
                .build();
    }

    public User toUserEntity(){
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.USER)
                .providerId(providerId)
                .authProvider(authProvider)
                .build();
    }
    public User toUserEntity(String encodePassword){
        return User.builder()
                .name(name)
                .email(email)
                .nickname(nickname)
                .gender(gender)
                .birthday(birthday)
                .picture(picture)
                .isInitProfile(false)
                .password(encodePassword)
                .role(Role.USER)
                .providerId(providerId)
                .authProvider(authProvider)
                .build();
    }
}
