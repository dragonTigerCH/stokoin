package com.bsc.stokoin.comment.domain.comment;

import com.bsc.stokoin.comment.presentation.dto.response.CommentResponseDto;
import com.bsc.stokoin.common.domain.BaseEntity;
import com.bsc.stokoin.common.exception.comment.CommentPostNotEqualsException;
import com.bsc.stokoin.common.exception.comment.CommentUserNotEqualsException;
import com.bsc.stokoin.post.domain.Post;
import com.bsc.stokoin.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_comment")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "comment_sequence_generator")
    @SequenceGenerator(
            name = "comment_sequence_generator",
            sequenceName = "comment_sequence",
            allocationSize = 100) // todo : 일단 캐싱해두는 시퀀스 값을 100으로 지정 협의 필요
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public CommentResponseDto toResponseDto(){
        return CommentResponseDto.builder()
                .id(this.id)
                .content(this.content)
                .user(this.user.toUserCommentResponseDto())
                .createdAt(this.getCreatedDate())
                .updatedAt(this.getLastModifiedDate())
                .build();
    }

    public void update(String content){
        this.content = content;
    }

    public void validUser(User user){
        if(!this.user.equals(user)){
            throw new CommentUserNotEqualsException();
        }
    }

    public void validPost(Post post){
        if(!this.post.equals(post)){
            throw new CommentPostNotEqualsException();
        }
    }
}
