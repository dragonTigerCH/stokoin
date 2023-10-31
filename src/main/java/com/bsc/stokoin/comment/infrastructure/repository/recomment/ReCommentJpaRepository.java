package com.bsc.stokoin.comment.infrastructure.repository.recomment;

import com.bsc.stokoin.comment.domain.comment.Comment;
import com.bsc.stokoin.comment.domain.recomment.ReComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReCommentJpaRepository extends JpaRepository<ReComment, Long> {
    Page<ReComment> findByCommentOrderByCreatedDateAsc(Comment comment, Pageable pageable);
    void deleteAllByComment(Comment comment);
}
