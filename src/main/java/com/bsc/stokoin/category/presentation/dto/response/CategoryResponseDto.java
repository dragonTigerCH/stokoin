package com.bsc.stokoin.category.presentation.dto.response;

import com.bsc.stokoin.category.domain.enums.CategoryEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponseDto {
    @Schema(description = "카테고리 ID", example = "1")
    private Long id;

    @Schema(description = "카테고리 코드", example = "STOCK")
    private CategoryEnums categoryCode;

    @Schema(description = "카테고리 설명", example = "주식")
    private String description;
}
