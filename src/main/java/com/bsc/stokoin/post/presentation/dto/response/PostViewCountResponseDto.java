package com.bsc.stokoin.post.presentation.dto.response;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostViewCountResponseDto {
    private Long id;
    private Long viewCount;
}
