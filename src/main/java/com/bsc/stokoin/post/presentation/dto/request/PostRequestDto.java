package com.bsc.stokoin.post.presentation.dto.request;

import com.bsc.stokoin.category.domain.Category;
import com.bsc.stokoin.category.domain.enums.CategoryEnums;
import com.bsc.stokoin.post.domain.Post;
import com.bsc.stokoin.user.domain.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class PostRequestDto {
    @NotEmpty
    @Size(min = 2, message = "제목은 2글자 이상 입력해주세요.")
    private String title;

    @NotEmpty
    @Size(min = 10, message = "내용은 10글자 이상 입력해주세요.")
    private String content;

    @NotEmpty
    private CategoryEnums categoryEnums;

    public Post toEntity(Category category, User user) {
        return Post.builder()
                .title(title)
                .content(content)
                .category(category)
                .user(user)
                .viewCount(0L)
                .build();
    }
}
