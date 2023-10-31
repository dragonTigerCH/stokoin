package com.bsc.stokoin.comment.domain.comment.repository;

import com.bsc.stokoin.comment.domain.comment.Comment;
import com.bsc.stokoin.post.domain.Post;
import com.bsc.stokoin.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CommentRepository {
    Page<Comment> findByPostOrderByCreatedDateAsc(Post post, Pageable pageable);
    Page<Comment> findByUserOrderByCreatedDateAsc(User user, Pageable pageable);
    Optional<Comment> findById(Long id);
    Comment save(Comment comment);
    void deleteById(Long id);
}
