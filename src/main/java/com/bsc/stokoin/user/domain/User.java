package com.bsc.stokoin.user.domain;

import com.bsc.stokoin.common.domain.BaseEntity;
import com.bsc.stokoin.user.domain.enums.AuthProvider;
import com.bsc.stokoin.user.domain.enums.Gender;
import com.bsc.stokoin.user.domain.enums.Role;
import com.bsc.stokoin.user.presentation.dto.request.UserSimpleInfoRequest;
import com.bsc.stokoin.user.presentation.dto.response.UserCommentResponseDto;
import com.bsc.stokoin.user.presentation.dto.response.UserPostResponseDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "tb_user")
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String email;
    private String password;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean isInitProfile;

    @Column(nullable = false)
    private String name;

    private String picture;
    private String providerId;
    private String nickname;
    private String birthday;
    @Enumerated(EnumType.STRING)
    private Gender gender;


//    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;


    public String getRoleKey(){
        return this.role.getKey();
    }


    public User updateBySocialProfile(String name, String picture){
        this.name = name;
        this.picture = picture;
        return this;
    }

    public Boolean validRole() {
        // 유저가 어드민인지 조사
        return this.role == Role.ADMIN;
    }

    public UserPostResponseDto toUserPostResponseDto(){
        return UserPostResponseDto.builder()
                .id(this.id)
                .email(this.email)
                .name(this.name)
                .nickname(this.nickname)
                .picture(this.picture)
                .isInitProfile(this.isInitProfile)
                .build();
    }

    public UserCommentResponseDto toUserCommentResponseDto(){
        return UserCommentResponseDto.builder()
                .id(this.id)
                .email(this.email)
                .name(this.name)
                .nickname(this.nickname)
                .picture(this.picture)
                .isInitProfile(this.isInitProfile)
                .build();
    }

    public void updateSimpleInfo(UserSimpleInfoRequest userSimpleInfoRequest) {
        this.isInitProfile = true;
        if (userSimpleInfoRequest.getNickname() != null)
            this.nickname = userSimpleInfoRequest.getNickname();
        if (userSimpleInfoRequest.getBirthday() != null)
            this.birthday = userSimpleInfoRequest.getBirthday();
        if (userSimpleInfoRequest.getGender() != null)
            this.gender = userSimpleInfoRequest.getGender();
    }
}
