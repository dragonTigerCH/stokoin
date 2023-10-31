package com.bsc.stokoin.post.presentation.dto.response;

import com.bsc.stokoin.category.presentation.dto.response.CategoryResponseDto;
import com.bsc.stokoin.user.presentation.dto.response.UserPostResponseDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private Long viewCount;
    private CategoryResponseDto category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserPostResponseDto user;
}
