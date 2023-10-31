package com.bsc.stokoin.comment.presentation.dto.response;

import com.bsc.stokoin.comment.domain.comment.Comment;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommentPageResponseDto {
    private List<CommentResponseDto> comments;
    private Long totalElements;
    private Integer totalPages;
    private Integer pageNo;
    private Integer pageSize;
    private Boolean last;

    @Builder
    public CommentPageResponseDto(Page<Comment> comments) {
        this.comments = comments.stream().map(Comment::toResponseDto).toList();
        this.totalElements = comments.getTotalElements();
        this.totalPages = comments.getTotalPages();
        this.pageNo = comments.getNumber();
        this.pageSize = comments.getSize();
        this.last = comments.isLast();
    }
}
