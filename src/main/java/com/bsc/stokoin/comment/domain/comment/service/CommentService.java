package com.bsc.stokoin.comment.domain.comment.service;

import com.bsc.stokoin.comment.domain.comment.Comment;
import com.bsc.stokoin.comment.domain.comment.repository.CommentRepository;
import com.bsc.stokoin.comment.presentation.dto.request.CommentRequestDto;
import com.bsc.stokoin.comment.presentation.dto.response.CommentPageResponseDto;
import com.bsc.stokoin.comment.presentation.dto.response.CommentResponseDto;
import com.bsc.stokoin.common.exception.comment.CommentNotFoundException;
import com.bsc.stokoin.common.exception.post.PostNotFoundException;
import com.bsc.stokoin.post.domain.Post;
import com.bsc.stokoin.post.domain.repository.PostRepository;
import com.bsc.stokoin.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
@RequiredArgsConstructor
public class CommentService implements CommentCommandUseCase {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Override
    public CommentPageResponseDto getCommentsByPostId(Long postId, Integer pageNo, Integer pageSize) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);

        return CommentPageResponseDto.builder().comments(
                commentRepository.findByPostOrderByCreatedDateAsc(post, getPageable(pageNo, pageSize))).build();
    }

    @Override
    public CommentPageResponseDto getCommentsByUser(User user, Integer pageNo, Integer pageSize) {
        return CommentPageResponseDto.builder().comments(
                commentRepository.findByUserOrderByCreatedDateAsc(user, getPageable(pageNo, pageSize))).build();
    }

    @Override
    public String deleteById(Long id, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);

        // 유저가 관리자가 아니면 댓글 작성자와 일치하는지 확인
        if(!user.validRole()) {
            comment.validUser(user);
        }

        try {
            commentRepository.deleteById(id);
            return "댓글 삭제에 성공했습니다.";
        } catch (Exception e) {
            return "댓글 삭제에 실패했습니다.";
        }
    }

    @Override
    public CommentResponseDto update(Long id, String content, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);
        comment.validUser(user);

        comment.update(content);

        return comment.toResponseDto();
    }

    @Override
    public CommentResponseDto save(CommentRequestDto commentRequestDto, Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);

        Comment comment = commentRequestDto.toEntity(user, post);

        return commentRepository.save(comment).toResponseDto();
    }

    @Override
    public CommentResponseDto findById(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);

        return comment.toResponseDto();
    }

    private Pageable getPageable(Integer pageNo, Integer pageSize) {
        return PageRequest.of(pageNo, pageSize);
    }
}
