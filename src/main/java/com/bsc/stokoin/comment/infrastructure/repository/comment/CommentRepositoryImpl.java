package com.bsc.stokoin.comment.infrastructure.repository.comment;

import com.bsc.stokoin.comment.domain.comment.Comment;
import com.bsc.stokoin.comment.domain.comment.repository.CommentRepository;
import com.bsc.stokoin.post.domain.Post;
import com.bsc.stokoin.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {
    private final CommentJpaRepository commentJpaRepository;

    @Override
    public Page<Comment> findByPostOrderByCreatedDateAsc(Post post, Pageable pageable) {
        return commentJpaRepository.findByPostOrderByCreatedDateAsc(post, pageable);
    }

    @Override
    public Page<Comment> findByUserOrderByCreatedDateAsc(User user, Pageable pageable) {
        return commentJpaRepository.findByUserOrderByCreatedDateAsc(user, pageable);
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return commentJpaRepository.findById(id);
    }

    @Override
    public Comment save(Comment comment) {
        return commentJpaRepository.save(comment);
    }

    @Override
    public void deleteById(Long id) {
        commentJpaRepository.deleteById(id);
    }
}
