package com.bsc.stokoin.category.application;

import com.bsc.stokoin.category.domain.service.CategoryCommandUseCase;
import com.bsc.stokoin.category.presentation.dto.request.CategoryRequestDto;
import com.bsc.stokoin.category.presentation.dto.response.CategoryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryFacade {
    private final CategoryCommandUseCase categoryCommandUseCase;

    public List<CategoryResponseDto> findAll() {
        return categoryCommandUseCase.findAll();
    }

    public CategoryResponseDto findById(Long id) {
        return categoryCommandUseCase.findById(id);
    }

    public CategoryResponseDto save(CategoryRequestDto categoryRequestDto) {
        return categoryCommandUseCase.save(categoryRequestDto);
    }

    public String deleteById(Long id) {
        return categoryCommandUseCase.deleteById(id);
    }

    public CategoryResponseDto update(Long id, CategoryRequestDto categoryRequestDto) {
        return categoryCommandUseCase.update(id, categoryRequestDto);
    }
}
