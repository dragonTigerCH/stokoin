package com.bsc.stokoin.comment.presentation.dto.request;

import com.bsc.stokoin.comment.domain.comment.Comment;
import com.bsc.stokoin.comment.domain.recomment.ReComment;
import com.bsc.stokoin.user.domain.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ReCommentRequestDto {
    @NotEmpty(message = "내용을 입력해주세요.")
    private String content;

    @Builder
    public ReComment toEntity(Comment comment, User user){
        return ReComment.builder()
                .content(this.content)
                .comment(comment)
                .user(user)
                .build();
    }
}
