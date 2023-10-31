package com.bsc.stokoin.comment.presentation.dto.request;

import com.bsc.stokoin.comment.domain.comment.Comment;
import com.bsc.stokoin.post.domain.Post;
import com.bsc.stokoin.user.domain.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class CommentRequestDto {
    @NotEmpty(message = "내용을 입력해주세요.")
    private String content;

    public Comment toEntity(User user, Post post) {
        return Comment.builder()
                .content(content)
                .user(user)
                .post(post)
                .build();
    }
}
