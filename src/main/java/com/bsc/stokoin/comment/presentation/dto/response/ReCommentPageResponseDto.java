package com.bsc.stokoin.comment.presentation.dto.response;


import com.bsc.stokoin.comment.domain.recomment.ReComment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class ReCommentPageResponseDto {
    private List<ReCommentResponseDto> reComments;
    private Long totalElements;
    private Integer totalPages;
    private Integer pageNo;
    private Integer pageSize;
    private Boolean last;

    @Builder
    public ReCommentPageResponseDto(Page<ReComment> reComments) {
        this.reComments = reComments.stream().map(ReComment::toResponseDto).toList();
        this.totalElements = reComments.getTotalElements();
        this.totalPages = reComments.getTotalPages();
        this.pageNo = reComments.getNumber();
        this.pageSize = reComments.getSize();
        this.last = reComments.isLast();
    }
}
