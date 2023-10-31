package com.bsc.stokoin.post.domain.service;

import com.bsc.stokoin.category.domain.Category;
import com.bsc.stokoin.category.domain.enums.CategoryEnums;
import com.bsc.stokoin.category.domain.repository.CategoryRepository;
import com.bsc.stokoin.common.exception.category.CategoryNotFoundException;
import com.bsc.stokoin.common.exception.post.PostNotFoundException;
import com.bsc.stokoin.common.exception.user.UserNotFoundException;
import com.bsc.stokoin.post.domain.Post;
import com.bsc.stokoin.post.domain.enums.PostEnums;
import com.bsc.stokoin.post.domain.repository.PostRepository;
import com.bsc.stokoin.post.presentation.dto.request.PostRequestDto;
import com.bsc.stokoin.post.presentation.dto.response.PostPageResponseDto;
import com.bsc.stokoin.post.presentation.dto.response.PostResponseDto;
import com.bsc.stokoin.post.presentation.dto.response.PostViewCountResponseDto;
import com.bsc.stokoin.user.domain.User;
import com.bsc.stokoin.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService implements PostCommandUseCase {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Override
    public PostPageResponseDto findByTitleContainsOrderByCreatedDateDesc(
            String title, Integer pageNo, Integer pageSize, PostEnums postEnums, CategoryEnums categoryEnums, Long userId) {
        if (userId != null) {
            userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        }

        return PostPageResponseDto.builder()
                .posts(postRepository.findByTitleContainsOrderByCreatedDateDesc(title, createPageable(pageNo, pageSize), postEnums, categoryEnums, userId))
                .build();
    }

    @Override
    public PostResponseDto findById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);

        return post.toPostResponseDto();
    }

    @Override
    public PostResponseDto save(PostRequestDto postRequestDto, CategoryEnums categoryEnums, User user) {
        Category category = categoryRepository.findByCategoryCode(categoryEnums).orElseThrow(CategoryNotFoundException::new);

        Post post = postRequestDto.toEntity(category, user);

        return postRepository.save(post).toPostResponseDto();
    }

    @Override
    public String deleteById(Long id, User user) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);

        // 유저가 관리자가 아니면 글을 작성한 사람인지 확인
        if(!user.validRole()) {
            post.validUser(user);
        }

        try {
            postRepository.deleteById(id);

            return "게시글 삭제에 성공했습니다.";
        } catch (Exception e) {
            return "게시글 삭제에 실패했습니다.";
        }
    }

    @Override
    public PostResponseDto update(Long id, PostRequestDto postRequestDto, Long categoryId, User user) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);

        Category category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
        post.validUser(user);
        post.update(postRequestDto, category, user);

        return post.toPostResponseDto();
    }

    @Override
    public PostViewCountResponseDto updateViewCount(Long id) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);

        post.updateViewCount();

        return post.toPostViewCountResponseDto();
    }

    private Pageable createPageable(Integer pageNo, Integer pageSize) {
        return PageRequest.of(pageNo, pageSize);
    }
}
