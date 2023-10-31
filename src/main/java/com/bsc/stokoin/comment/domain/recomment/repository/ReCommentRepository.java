package com.bsc.stokoin.comment.domain.recomment.repository;

import com.bsc.stokoin.comment.domain.comment.Comment;
import com.bsc.stokoin.comment.domain.recomment.ReComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ReCommentRepository {
    Page<ReComment> findByCommentOrderByCreatedDateAsc(Comment comment, Pageable pageable);

    ReComment save(ReComment reComment);

    void delete(ReComment reComment);

    void deleteAllByComment(Comment comment);

    Optional<ReComment> findById(long id);
}
