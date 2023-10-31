package com.bsc.stokoin.comment.domain.recomment.service;

import com.bsc.stokoin.comment.presentation.dto.request.ReCommentRequestDto;
import com.bsc.stokoin.comment.presentation.dto.response.ReCommentPageResponseDto;
import com.bsc.stokoin.comment.presentation.dto.response.ReCommentResponseDto;
import com.bsc.stokoin.user.domain.User;

public interface ReCommentCommandUseCase {
    ReCommentPageResponseDto getReCommentsByCommentId(Long commentId, Integer pageNo, Integer pageSize);

    ReCommentResponseDto save(ReCommentRequestDto reCommentRequestDto, Long commentId, User user);

    String deleteById(Long id, User user);

    String deleteAllByComment(Long commentId, User user);

    ReCommentResponseDto findById(Long id);

    ReCommentResponseDto update(Long id, ReCommentRequestDto reCommentRequestDto, User user);
}
