package com.bsc.stokoin.category.presentation.dto.request;

import com.bsc.stokoin.category.domain.Category;
import com.bsc.stokoin.category.domain.enums.CategoryEnums;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class CategoryRequestDto {
    @NotEmpty(message = "카테고리 코드는 필수 입력 값입니다.")
    private CategoryEnums categoryCode;

    @NotEmpty(message = "카테고리 설명은 필수 입력 값입니다.")
    private String description;

    public Category toEntity() {
        return Category.builder()
                .categoryCode(categoryCode)
                .description(description)
                .build();
    }
}
