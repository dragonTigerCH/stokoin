package com.bsc.stokoin.category.domain.service;

import com.bsc.stokoin.category.presentation.dto.request.CategoryRequestDto;
import com.bsc.stokoin.category.presentation.dto.response.CategoryResponseDto;

import java.util.List;

public interface CategoryCommandUseCase {
    List<CategoryResponseDto> findAll();
    CategoryResponseDto findById(Long id);
    CategoryResponseDto save(CategoryRequestDto categoryRequestDto);
    String deleteById(Long id);

    CategoryResponseDto update(Long id, CategoryRequestDto categoryRequestDto);
}
