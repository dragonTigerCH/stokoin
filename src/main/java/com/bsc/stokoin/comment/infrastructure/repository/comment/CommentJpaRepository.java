package com.bsc.stokoin.comment.infrastructure.repository.comment;

import com.bsc.stokoin.comment.domain.comment.Comment;
import com.bsc.stokoin.post.domain.Post;
import com.bsc.stokoin.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByPostOrderByCreatedDateAsc(Post post, Pageable pageable);
    Page<Comment> findByUserOrderByCreatedDateAsc(User user, Pageable pageable);
}
