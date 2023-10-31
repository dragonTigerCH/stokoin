package com.bsc.stokoin.user.presentation.dto.request;

import com.bsc.stokoin.user.domain.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;



@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserSimpleInfoRequest {

    @NotBlank(message = "닉네임을 입력하세요.")
    @Size(max = 50,message = "50자 이내로 입력하세요.")
    private String nickname;
    @NotBlank(message = "생년월일을 입력하세요.")
    @Size(max = 50,message = "50자 이내로 입력하세요.")
    private String birthday;
    @NotNull(message = "성별을 입력하세요.")
    private Gender gender;

}
