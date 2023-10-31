package com.bsc.stokoin.comment.presentation.dto.response;

import com.bsc.stokoin.user.presentation.dto.response.UserCommentResponseDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseDto {
    private Long id;
    private String content;
    private UserCommentResponseDto user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
