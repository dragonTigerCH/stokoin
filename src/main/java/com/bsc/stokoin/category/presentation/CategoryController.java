package com.bsc.stokoin.category.presentation;

import com.bsc.stokoin.category.application.CategoryFacade;
import com.bsc.stokoin.category.presentation.dto.request.CategoryRequestDto;
import com.bsc.stokoin.category.presentation.dto.response.CategoryResponseDto;
import com.bsc.stokoin.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "카테고리 API")
@RestController
@RequestMapping("/category")
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryFacade categoryFacade;

    @Operation(summary = "전체 카테고리 조회 API", description = "<br>" +
            "카테고리 정보 전체 조회 API<br>")
    @GetMapping
    public ResponseEntity<CommonResponse<List<CategoryResponseDto>>> findAll() {
        return ResponseEntity.ok(CommonResponse.success(categoryFacade.findAll()));
    }

    @Operation(summary = "단일 카테고리 조회 API", description = "<br>" +
            "카테고리 정보 단일 조회 API<br>")
    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<CategoryResponseDto>> findById(
            @PathVariable Long id) {
        return ResponseEntity.ok(CommonResponse.success(categoryFacade.findById(id)));
    }

    @Operation(summary = "카테고리 저장 API [관리자]", description = "<br>" +
            "카테고리 정보 를 받아와서 데이터베이스에 저장한다.<br>")
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CommonResponse<CategoryResponseDto>> save(
            @Valid @RequestBody CategoryRequestDto categoryRequestDto) {
        return new ResponseEntity<>(CommonResponse.success(categoryFacade.save(categoryRequestDto)),
                HttpStatus.CREATED);
    }

    @Operation(summary = "카테고리 수정 API [관리자]", description = "<br>" +
            "카테고리 정보를 받아와서 조회 후 정보를 수정한다.<br>")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CommonResponse<CategoryResponseDto>> update(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequestDto categoryRequestDto) {
        return ResponseEntity.ok(CommonResponse.success(categoryFacade.update(id, categoryRequestDto)));
    }

    @Operation(summary = "카테고리 삭제 API [관리자]", description = "<br>" +
            "카테고리 구분 id를 받아와 조회 후 삭제한다.<br>")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CommonResponse<String>> deleteById(
            @PathVariable Long id) {
        return ResponseEntity.ok(CommonResponse.success(categoryFacade.deleteById(id)));
    }
}
