package com.bsc.stokoin.comment.domain.recomment;

import com.bsc.stokoin.comment.domain.comment.Comment;
import com.bsc.stokoin.comment.presentation.dto.response.ReCommentResponseDto;
import com.bsc.stokoin.common.domain.BaseEntity;
import com.bsc.stokoin.common.exception.comment.CommentUserNotEqualsException;
import com.bsc.stokoin.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_recomment")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ReComment extends BaseEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "recomment_sequence_generator")
    @SequenceGenerator(
            name = "recomment_sequence_generator",
            sequenceName = "recomment_sequence",
            allocationSize = 100)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void validUser(User user){
        if(!this.user.equals(user)){
            throw new CommentUserNotEqualsException();
        }
    }

    public void update(String content){
        this.content = content;
    }

    public ReCommentResponseDto toResponseDto(){
        return ReCommentResponseDto.builder()
                .id(this.id)
                .content(this.content)
                .user(this.user.toUserCommentResponseDto())
                .createdAt(this.getCreatedDate())
                .updatedAt(this.getLastModifiedDate())
                .build();
    }
}
