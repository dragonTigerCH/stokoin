package com.bsc.stokoin.category.domain.service;

import com.bsc.stokoin.category.domain.Category;
import com.bsc.stokoin.category.domain.repository.CategoryRepository;
import com.bsc.stokoin.category.presentation.dto.request.CategoryRequestDto;
import com.bsc.stokoin.category.presentation.dto.response.CategoryResponseDto;
import com.bsc.stokoin.common.exception.category.CategoryNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService implements CategoryCommandUseCase {
    private final CategoryRepository categoryRepository;


    @Override
    public List<CategoryResponseDto> findAll() {
        return categoryRepository.findAll().stream().map(Category::toResponseDto).toList();
    }

    @Override
    public CategoryResponseDto findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                CategoryNotFoundException::new
        ).toResponseDto();
    }

    @Override
    public CategoryResponseDto save(CategoryRequestDto categoryRequestDto) {
        return categoryRepository.save(categoryRequestDto.toEntity()).toResponseDto();
    }

    @Override
    public String deleteById(Long id) {
        categoryRepository.deleteById(id);
        return "카테고리를 삭제했습니다.";
    }

    @Override
    public CategoryResponseDto update(Long id, CategoryRequestDto categoryRequestDto) {
        Category category = categoryRepository.findById(id).orElseThrow(
                CategoryNotFoundException::new
        );
        category.update(categoryRequestDto);
        return category.toResponseDto();
    }
}
