package com.bsc.stokoin.comment.domain.recomment.service;

import com.bsc.stokoin.comment.domain.comment.Comment;
import com.bsc.stokoin.comment.domain.recomment.ReComment;
import com.bsc.stokoin.comment.domain.comment.repository.CommentRepository;
import com.bsc.stokoin.comment.domain.recomment.repository.ReCommentRepository;
import com.bsc.stokoin.comment.presentation.dto.request.ReCommentRequestDto;
import com.bsc.stokoin.comment.presentation.dto.response.ReCommentPageResponseDto;
import com.bsc.stokoin.comment.presentation.dto.response.ReCommentResponseDto;
import com.bsc.stokoin.common.exception.comment.CommentNotFoundException;
import com.bsc.stokoin.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReCommentService implements ReCommentCommandUseCase {
    private final ReCommentRepository reCommentRepository;
    private final CommentRepository commentRepository;


    @Override
    public ReCommentPageResponseDto getReCommentsByCommentId(Long commentId, Integer pageNo, Integer pageSize) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        return ReCommentPageResponseDto.builder().reComments(
                reCommentRepository.findByCommentOrderByCreatedDateAsc(comment, pageable)
        ).build();
    }

    @Override
    public ReCommentResponseDto save(ReCommentRequestDto reCommentRequestDto, Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);

        return reCommentRepository.save(reCommentRequestDto.toEntity(comment, user)).toResponseDto();
    }

    @Override
    public String deleteById(Long id, User user) {
        ReComment reComment = reCommentRepository.findById(id).orElseThrow(CommentNotFoundException::new);

        if (!user.validRole()) {
            reComment.validUser(user);
        }

        try {
            reCommentRepository.delete(reComment);
            return "댓글 삭제에 성공했습니다.";
        } catch (Exception e) {
            return "댓글 삭제에 실패했습니다.";
        }
    }

    @Override
    public String deleteAllByComment(Long commentId, User user) {
        return null;
    }

    @Override
    public ReCommentResponseDto findById(Long id) {
        ReComment reComment = reCommentRepository.findById(id).orElseThrow(CommentNotFoundException::new);
        return reComment.toResponseDto();
    }

    @Override
    public ReCommentResponseDto update(Long id, ReCommentRequestDto reCommentRequestDto, User user) {
        ReComment reComment = reCommentRepository.findById(id).orElseThrow(CommentNotFoundException::new);

        reComment.validUser(user);


        reComment.update(reCommentRequestDto.getContent());

        return reComment.toResponseDto();
    }
}
