package com.bsc.stokoin.comment.presentation.dto.response;

import com.bsc.stokoin.user.presentation.dto.response.UserCommentResponseDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ReCommentResponseDto {
    private Long id;
    private String content;
    private UserCommentResponseDto user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
