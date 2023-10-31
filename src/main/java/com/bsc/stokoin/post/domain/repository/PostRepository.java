package com.bsc.stokoin.post.domain.repository;

import com.bsc.stokoin.category.domain.Category;
import com.bsc.stokoin.category.domain.enums.CategoryEnums;
import com.bsc.stokoin.post.domain.Post;
import com.bsc.stokoin.post.domain.enums.PostEnums;
import com.bsc.stokoin.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PostRepository {
    Page<Post> findByTitleContainsOrderByCreatedDateDesc(
            String title, Pageable pageable, PostEnums postEnums, CategoryEnums categoryEnums, Long userId); // todo : 엘라스틱 서치로 효율 높이기
    Optional<Post> findById(Long id);
    Page<Post> findByUserOrderByCreatedDateDesc(User user, Pageable pageable);
    Post save(Post post);
    void deleteById(Long id);
}
