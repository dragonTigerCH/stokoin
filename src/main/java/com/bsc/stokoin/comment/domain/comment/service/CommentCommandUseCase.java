package com.bsc.stokoin.comment.domain.comment.service;

import com.bsc.stokoin.comment.presentation.dto.request.CommentRequestDto;
import com.bsc.stokoin.comment.presentation.dto.response.CommentPageResponseDto;
import com.bsc.stokoin.comment.presentation.dto.response.CommentResponseDto;
import com.bsc.stokoin.user.domain.User;

public interface CommentCommandUseCase {
    CommentPageResponseDto getCommentsByPostId(Long postId, Integer pageNo, Integer pageSize);
    CommentPageResponseDto getCommentsByUser(User user, Integer pageNo, Integer pageSize);

    String deleteById(Long id, User user);

    CommentResponseDto update(Long id, String content, User user);

    CommentResponseDto save(CommentRequestDto commentRequestDto, Long postId, User user);

    CommentResponseDto findById(Long id);
}
