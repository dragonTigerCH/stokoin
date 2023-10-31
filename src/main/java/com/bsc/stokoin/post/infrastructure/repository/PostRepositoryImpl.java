package com.bsc.stokoin.post.infrastructure.repository;

import com.bsc.stokoin.category.domain.Category;
import com.bsc.stokoin.category.domain.enums.CategoryEnums;
import com.bsc.stokoin.post.domain.Post;
import com.bsc.stokoin.post.domain.enums.PostEnums;
import com.bsc.stokoin.post.domain.repository.PostRepository;
import com.bsc.stokoin.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {
    private final PostJpaRepository postJpaRepository;
    private final PostQuerydslRepository postQuerydslRepository;

    @Override
    public Page<Post> findByTitleContainsOrderByCreatedDateDesc(
            String title, Pageable pageable, PostEnums postEnums, CategoryEnums categoryEnums, Long userId) {
        return postQuerydslRepository.findByTitleContainsOrderByCreatedDateDesc(title, pageable, postEnums, categoryEnums, userId);
    }

    @Override
    public Optional<Post> findById(Long id) {
        return postJpaRepository.findById(id);
    }

    @Override
    public Page<Post> findByUserOrderByCreatedDateDesc(User user, Pageable pageable) {
        return postJpaRepository.findByUserOrderByCreatedDateDesc(user, pageable);
    }

    @Override
    public Post save(Post post) {
        return postJpaRepository.save(post);
    }

    @Override
    public void deleteById(Long id) {
        postJpaRepository.deleteById(id);
    }
}
