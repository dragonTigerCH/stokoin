package com.bsc.stokoin.post.presentation;

import com.bsc.stokoin.category.domain.enums.CategoryEnums;
import com.bsc.stokoin.common.CommonResponse;
import com.bsc.stokoin.common.LoginUser;
import com.bsc.stokoin.post.application.PostFacade;
import com.bsc.stokoin.post.domain.enums.PostEnums;
import com.bsc.stokoin.post.presentation.dto.request.PostRequestDto;
import com.bsc.stokoin.post.presentation.dto.response.PostPageResponseDto;
import com.bsc.stokoin.post.presentation.dto.response.PostResponseDto;
import com.bsc.stokoin.post.presentation.dto.response.PostViewCountResponseDto;
import com.bsc.stokoin.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "게시글 API")
@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostRestController {
    private final PostFacade postFacade;

    @Operation(summary = "게시글 전체 검색 API [Pagination]", description = "<br>" +
            "게시글 제목 검색 API<br>" +
            "포스트 enum 을 이용해서 검색을 필터링한다. <br> "+
            "타입이 user일 경우 받는 유저 시퀀스로 유저를 검색하여 작성한 글을 보여준다. <br>" +
            "타입이 카테고리일 경우 해당 카테고리에 작성된 글만 보여준다.<br>")
    @GetMapping
    public ResponseEntity<CommonResponse<PostPageResponseDto>> findByTitleContainsOrderByCreatedDateDesc(
            @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "title", defaultValue = "") String title,
            @RequestParam(required = false) PostEnums postEnums,
            @RequestParam(required = false) CategoryEnums categoryEnums,
            @RequestParam(required = false) Long userId
            ) {
        return ResponseEntity.ok(CommonResponse.success(postFacade.findByTitleContainsOrderByCreatedDateDesc(title, pageNo, pageSize,postEnums,categoryEnums, userId)));
    }

    @Operation(summary = "게시글 상세 조회 API", description = "<br>" +
            "게시글 구분 id를 받아와 조회한다.<br>")
    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<PostResponseDto>> findById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(CommonResponse.success(postFacade.findById(id)));
    }

    @Operation(summary = "게시글 저장 API", description = "<br>" +
            "게시글 정보 를 받아와서 데이터베이스에 저장한다.<br>")
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CommonResponse<PostResponseDto>> save(
            @LoginUser User user,
            @RequestBody PostRequestDto postRequestDto
    ) {
        return ResponseEntity.ok(CommonResponse.success(postFacade.save(postRequestDto,postRequestDto.getCategoryEnums() ,user)));
    }

    @Operation(summary = "게시글 수정 API", description = "<br>" +
            "게시글 정보를 받아와서 조회 후 정보를 수정한다.<br>" +
            "게시글을 작성한 유저가 아니라면 수정하지 못하게한다.<br>")
    @PutMapping("/{id}/{categoryId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CommonResponse<PostResponseDto>> update(
            @LoginUser User user,
            @PathVariable Long postId,
            @PathVariable Long categoryId,
            @RequestBody PostRequestDto postRequestDto
    ) {
        return ResponseEntity.ok(CommonResponse.success(postFacade.update(postId, postRequestDto, categoryId, user)));
    }

    @Operation(summary = "게시글 삭제 API", description = "<br>" +
            "게시글 구분 id를 받아와 조회 후 삭제한다.<br>" +
            "게시글을 작성한 유저가 아니라면 삭제하지 못하게한다.<br>")
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CommonResponse<String>> deleteById(
            @PathVariable Long id,
            @LoginUser User user
    ) {
        return ResponseEntity.ok(CommonResponse.success(postFacade.deleteById(id, user)));
    }

    @Operation(summary = "게시글 조회수 증가 API", description = "<br>" +
            "게시글 구분 id를 받아와 조회수를 증가한다.<br>")
    @PutMapping("/view/{id}")
    public ResponseEntity<CommonResponse<PostViewCountResponseDto>> updateViewCount(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(CommonResponse.success(postFacade.updateViewCount(id)));
    }
}










