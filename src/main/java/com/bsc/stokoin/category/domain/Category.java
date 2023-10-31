package com.bsc.stokoin.category.domain;

import com.bsc.stokoin.category.domain.enums.CategoryEnums;
import com.bsc.stokoin.category.presentation.dto.request.CategoryRequestDto;
import com.bsc.stokoin.category.presentation.dto.response.CategoryResponseDto;
import com.bsc.stokoin.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_category")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CategoryEnums categoryCode;

    private String description;

    public CategoryResponseDto toResponseDto() {
        return CategoryResponseDto.builder()
                .id(id)
                .categoryCode(categoryCode)
                .description(description)
                .build();
    }

    public void update(CategoryRequestDto categoryRequestDto) {
        this.categoryCode = categoryRequestDto.getCategoryCode();
        this.description = categoryRequestDto.getDescription();
    }
}
