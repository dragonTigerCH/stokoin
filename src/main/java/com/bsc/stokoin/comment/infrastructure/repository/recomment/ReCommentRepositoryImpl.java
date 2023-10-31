package com.bsc.stokoin.comment.infrastructure.repository.recomment;

import com.bsc.stokoin.comment.domain.comment.Comment;
import com.bsc.stokoin.comment.domain.recomment.ReComment;
import com.bsc.stokoin.comment.domain.recomment.repository.ReCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReCommentRepositoryImpl implements ReCommentRepository {
    private final ReCommentJpaRepository reCommentJpaRepository;


    @Override
    public Page<ReComment> findByCommentOrderByCreatedDateAsc(Comment comment, Pageable pageable) {
        return reCommentJpaRepository.findByCommentOrderByCreatedDateAsc(comment, pageable);
    }

    @Override
    public ReComment save(ReComment reComment) {
        return reCommentJpaRepository.save(reComment);
    }

    @Override
    public void delete(ReComment reComment) {
        reCommentJpaRepository.delete(reComment);
    }

    @Override
    public void deleteAllByComment(Comment comment) {
        reCommentJpaRepository.deleteAllByComment(comment);
    }

    @Override
    public Optional<ReComment> findById(long id) {
        return reCommentJpaRepository.findById(id);
    }
}
