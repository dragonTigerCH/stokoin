package com.bsc.stokoin.comment.presentation;

import com.bsc.stokoin.comment.application.CommentFacade;
import com.bsc.stokoin.comment.presentation.dto.request.CommentRequestDto;
import com.bsc.stokoin.comment.presentation.dto.response.CommentPageResponseDto;
import com.bsc.stokoin.comment.presentation.dto.response.CommentResponseDto;
import com.bsc.stokoin.common.CommonResponse;
import com.bsc.stokoin.common.LoginUser;
import com.bsc.stokoin.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "댓글 API")
@RestController
@RequestMapping("/comment")
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class CommentRestController {
    private final CommentFacade commentFacade;

    @Operation(summary = "댓글 상세 조회 API", description = "<br>" +
            "댓글 상세 조회 API<br>")
    @GetMapping("/detail/{id}")
    public ResponseEntity<CommonResponse<CommentResponseDto>> findById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(CommonResponse.success(commentFacade.findById(id)));
    }

    @Operation(summary = "게시글에 대한 댓글 조회 API [Pagination]", description = "<br>" +
            "게시글에 대한 댓글 정보 전체 조회 API<br>")
    @GetMapping("/post/{postId}")
    public ResponseEntity<CommonResponse<CommentPageResponseDto>> findByPostIdOrderByCreatedDateAsc(
            @PathVariable Long postId,
            @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        return ResponseEntity.ok(CommonResponse.success(commentFacade.getCommentsByPostId(postId, pageNo, pageSize)));
    }

    @Operation(summary = "유저별 댓글 조회 API [Pagination]", description = "<br>" +
            "유저별 댓글 정보 전체 조회 API<br>")
    @GetMapping("/user/{userId}")
    public ResponseEntity<CommonResponse<CommentPageResponseDto>> findByUserIdOrderByCreatedDateAsc(
            @LoginUser User user,
            @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        return ResponseEntity.ok(CommonResponse.success(commentFacade.getCommentsByUser(user, pageNo, pageSize)));
    }

    @Operation(summary = "댓글 수정 API", description = "<br>" +
            "댓글 수정 API<br>")
    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<CommentResponseDto>> update(
            @PathVariable Long id,
            @Valid @RequestBody String content,
            @LoginUser User user
    ) {
        return ResponseEntity.ok(CommonResponse.success(commentFacade.update(id, content, user)));
    }

    @Operation(summary = "댓글 삭제 API", description = "<br>" +
            "댓글 삭제 API<br>")
    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<String>> deleteById(
            @PathVariable Long id,
            @LoginUser User user
    ) {
        return ResponseEntity.ok(CommonResponse.success(commentFacade.deleteById(id, user)));
    }

    @Operation(summary = "댓글 저장 API", description = "<br>" +
            "댓글 저장 API<br>")
    @PostMapping("/{postId}")
    public ResponseEntity<CommonResponse<CommentResponseDto>> save(
            @PathVariable Long postId,
            @Valid @RequestBody CommentRequestDto commentRequestDto,
            @LoginUser User user
    ) {
        return ResponseEntity.ok(CommonResponse.success(commentFacade.save(commentRequestDto, postId, user)));
    }
}














