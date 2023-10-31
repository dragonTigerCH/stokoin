package com.bsc.stokoin.post.domain;

import com.bsc.stokoin.category.domain.Category;
import com.bsc.stokoin.common.domain.BaseEntity;
import com.bsc.stokoin.common.exception.post.PostUserNotEqualsException;
import com.bsc.stokoin.post.presentation.dto.request.PostRequestDto;
import com.bsc.stokoin.post.presentation.dto.response.PostResponseDto;
import com.bsc.stokoin.post.presentation.dto.response.PostViewCountResponseDto;
import com.bsc.stokoin.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "tb_post")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // todo : 시퀀스 어떤 타입으로 할지 고민
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Long viewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public PostResponseDto toPostResponseDto(){
        return PostResponseDto.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .viewCount(this.viewCount)
                .category(this.category.toResponseDto())
                .user(this.user.toUserPostResponseDto())
                .createdAt(this.getCreatedDate())
                .updatedAt(this.getLastModifiedDate())
                .build();
    }

    public PostViewCountResponseDto toPostViewCountResponseDto(){
        return PostViewCountResponseDto.builder()
                .id(this.id)
                .viewCount(this.viewCount)
                .build();
    }

    public void update(PostRequestDto postRequestDto, Category category, User user){
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.category = category;
        this.user = user;
    }

    public void validUser(User user){
        if (!this.user.equals(user)) throw new PostUserNotEqualsException();
    }

    public void updateViewCount(){
        this.viewCount++;
    }

}
