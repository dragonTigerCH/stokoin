package com.bsc.stokoin.comment.application;

import com.bsc.stokoin.comment.domain.recomment.service.ReCommentCommandUseCase;
import com.bsc.stokoin.comment.presentation.dto.request.ReCommentRequestDto;
import com.bsc.stokoin.comment.presentation.dto.response.ReCommentPageResponseDto;
import com.bsc.stokoin.comment.presentation.dto.response.ReCommentResponseDto;
import com.bsc.stokoin.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReCommentFacade {
    private final ReCommentCommandUseCase reCommentCommandUseCase;

    public ReCommentPageResponseDto getReCommentsByCommentId(Long commentId, Integer pageNo, Integer pageSize) {
        return reCommentCommandUseCase.getReCommentsByCommentId(commentId, pageNo, pageSize);
    }

    public String deleteById(Long id, User user) {
        return reCommentCommandUseCase.deleteById(id, user);
    }

    public ReCommentResponseDto save(Long commentId, ReCommentRequestDto reCommentRequestDto, User user) {
        return reCommentCommandUseCase.save(reCommentRequestDto, commentId, user);
    }

    public ReCommentResponseDto update(Long id, ReCommentRequestDto reCommentRequestDto, User user) {
        return reCommentCommandUseCase.update(id, reCommentRequestDto, user);
    }

    public ReCommentResponseDto findById(Long id) {
        return reCommentCommandUseCase.findById(id);
    }
}
