package com.bsc.stokoin.post.application;

import com.bsc.stokoin.category.domain.enums.CategoryEnums;
import com.bsc.stokoin.post.domain.enums.PostEnums;
import com.bsc.stokoin.post.domain.service.PostCommandUseCase;
import com.bsc.stokoin.post.presentation.dto.request.PostRequestDto;
import com.bsc.stokoin.post.presentation.dto.response.PostPageResponseDto;
import com.bsc.stokoin.post.presentation.dto.response.PostResponseDto;
import com.bsc.stokoin.post.presentation.dto.response.PostViewCountResponseDto;
import com.bsc.stokoin.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostFacade {
    private final PostCommandUseCase postCommandUseCase;

    public PostPageResponseDto findByTitleContainsOrderByCreatedDateDesc(
            String title, Integer pageNo, Integer pageSize, PostEnums postEnums, CategoryEnums categoryEnums, Long userId) {
        return postCommandUseCase.findByTitleContainsOrderByCreatedDateDesc(title, pageNo, pageSize, postEnums, categoryEnums, userId);
    }

    public String deleteById(Long id, User user) {
        return postCommandUseCase.deleteById(id, user);
    }

    public PostViewCountResponseDto updateViewCount(Long id) {
        return postCommandUseCase.updateViewCount(id);
    }

    public PostResponseDto findById(Long id) {
        return postCommandUseCase.findById(id);
    }

    public PostResponseDto save(PostRequestDto postRequestDto, CategoryEnums categoryEnums, User user) {
        return postCommandUseCase.save(postRequestDto, categoryEnums, user);
    }

    public PostResponseDto update(Long id, PostRequestDto postRequestDto, Long categoryId, User user) {
        return postCommandUseCase.update(id, postRequestDto, categoryId, user);
    }
}
