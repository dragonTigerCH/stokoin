package com.bsc.stokoin.comment.application;

import com.bsc.stokoin.comment.domain.comment.service.CommentCommandUseCase;
import com.bsc.stokoin.comment.presentation.dto.request.CommentRequestDto;
import com.bsc.stokoin.comment.presentation.dto.response.CommentPageResponseDto;
import com.bsc.stokoin.comment.presentation.dto.response.CommentResponseDto;
import com.bsc.stokoin.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentFacade {
    private final CommentCommandUseCase commentCommandUseCase;

    public String deleteById(Long id, User user) {
        return commentCommandUseCase.deleteById(id, user);
    }

    public CommentResponseDto update(Long id, String content, User user) {
        return commentCommandUseCase.update(id, content, user);
    }

    public CommentPageResponseDto getCommentsByPostId(Long postId, Integer pageNo, Integer pageSize) {
        return commentCommandUseCase.getCommentsByPostId(postId, pageNo, pageSize);
    }

    public CommentResponseDto save(CommentRequestDto commentRequestDto,Long postId , User user) {
        return commentCommandUseCase.save(commentRequestDto, postId, user);
    }

    public CommentPageResponseDto getCommentsByUser(User user, Integer pageNo, Integer pageSize) {
        return commentCommandUseCase.getCommentsByUser(user, pageNo, pageSize);
    }

    public CommentResponseDto findById(Long id) {
        return commentCommandUseCase.findById(id);
    }
}
