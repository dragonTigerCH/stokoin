package com.bsc.stokoin.user.presentation.dto.response;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserPostResponseDto {
    private Long id;
    private String email;
    private String name;
    private String nickname;
    private String picture;
    private Boolean isInitProfile;
}
