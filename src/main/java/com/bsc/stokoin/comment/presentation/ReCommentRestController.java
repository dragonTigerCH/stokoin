package com.bsc.stokoin.comment.presentation;

import com.bsc.stokoin.comment.application.ReCommentFacade;
import com.bsc.stokoin.comment.presentation.dto.request.ReCommentRequestDto;
import com.bsc.stokoin.comment.presentation.dto.response.ReCommentPageResponseDto;
import com.bsc.stokoin.comment.presentation.dto.response.ReCommentResponseDto;
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

@Tag(name = "대댓글 API")
@RestController
@RequestMapping("/recomment")
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class ReCommentRestController {
    private final ReCommentFacade reCommentFacade;

    @Operation(summary = "댓글에 대한 대댓글 조회 API [Pagination]", description = "<br>" +
            "댓글에 대한 대댓글 정보 전체 조회 API<br>")
    @GetMapping("/comment/{commentId}")
    public ResponseEntity<CommonResponse<ReCommentPageResponseDto>> findByCommentIdOrderByCreatedDateAsc(
            @PathVariable Long commentId,
            @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        return ResponseEntity.ok(CommonResponse.success(reCommentFacade.getReCommentsByCommentId(commentId, pageNo, pageSize)));
    }

    @Operation(summary = "대댓글 삭제 API", description = "<br>" +
            "대댓글 삭제 API<br>" +
            "요청한 유저가 관리자 아닐시 작성한 유저인지 검사를 진행한다.<br>")
    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<String>> deleteById(
            @PathVariable Long id,
            @LoginUser User user
            ) {
        return ResponseEntity.ok(CommonResponse.success(reCommentFacade.deleteById(id, user)));
    }

    @Operation(summary = "대댓글 수정 API", description = "<br>" +
            "대댓글 수정 API<br>" +
            "요청한 유저가 작성한 댓글만 수정 가능<br>")
    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<ReCommentResponseDto>> update(
            @PathVariable Long id,
            @Valid @RequestBody ReCommentRequestDto reCommentRequestDto,
            @LoginUser User user
    ) {
        return ResponseEntity.ok(CommonResponse.success(reCommentFacade.update(id,reCommentRequestDto, user)));
    }

    @Operation(summary = "대댓글 상세 조회 API", description = "<br>" +
            "대댓글 상세 조회 API<br>")
    @GetMapping("/detail/{id}")
    public ResponseEntity<CommonResponse<ReCommentResponseDto>> findById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(CommonResponse.success(reCommentFacade.findById(id)));
    }

    @Operation(summary = "대댓글 등록 API", description = "<br>" +
            "대댓글 등록 API<br>")
    @PostMapping("/{commentId}")
    public ResponseEntity<CommonResponse<ReCommentResponseDto>> save(
            @PathVariable Long commentId,
            @Valid @RequestBody ReCommentRequestDto reCommentRequestDto,
            @LoginUser User user
    ) {
        return ResponseEntity.ok(CommonResponse.success(reCommentFacade.save(commentId, reCommentRequestDto, user)));
    }
}
















