package com.bsc.stokoin.post.domain.service;

import com.bsc.stokoin.category.domain.enums.CategoryEnums;
import com.bsc.stokoin.post.domain.enums.PostEnums;
import com.bsc.stokoin.post.presentation.dto.request.PostRequestDto;
import com.bsc.stokoin.post.presentation.dto.response.PostPageResponseDto;
import com.bsc.stokoin.post.presentation.dto.response.PostResponseDto;
import com.bsc.stokoin.post.presentation.dto.response.PostViewCountResponseDto;
import com.bsc.stokoin.user.domain.User;
import org.springframework.data.domain.Page;

public interface PostCommandUseCase {
    PostPageResponseDto findByTitleContainsOrderByCreatedDateDesc(
            String title, Integer pageNo, Integer pageSize, PostEnums postEnums, CategoryEnums categoryEnums, Long userId);
    PostResponseDto findById(Long id);
    PostResponseDto save(PostRequestDto postRequestDto, CategoryEnums categoryEnums, User user);

    String deleteById(Long id, User user);

    PostResponseDto update(Long id, PostRequestDto postRequestDto, Long categoryId, User user);

    PostViewCountResponseDto updateViewCount(Long id);
}
