package com.bsc.stokoin.post.presentation.dto.response;


import com.bsc.stokoin.post.domain.Post;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PostPageResponseDto {
    private List<PostResponseDto> posts;
    private Long totalElements;
    private Integer totalPages;
    private Integer pageNo;
    private Integer pageSize;
    private Boolean last;

    @Builder
    public PostPageResponseDto(Page<Post> posts) {
        this.posts = posts.stream().map(Post::toPostResponseDto).toList();
        this.totalElements = posts.getTotalElements();
        this.totalPages = posts.getTotalPages();
        this.pageNo = posts.getNumber();
        this.pageSize = posts.getSize();
        this.last = posts.isLast();
    }

}
