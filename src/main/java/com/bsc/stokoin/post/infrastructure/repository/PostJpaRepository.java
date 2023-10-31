package com.bsc.stokoin.post.infrastructure.repository;

import com.bsc.stokoin.category.domain.Category;
import com.bsc.stokoin.post.domain.Post;
import com.bsc.stokoin.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<Post, Long> {
    Page<Post> findByUserOrderByCreatedDateDesc(User user, Pageable pageable);
}
