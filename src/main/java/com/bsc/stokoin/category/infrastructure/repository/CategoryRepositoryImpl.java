package com.bsc.stokoin.category.infrastructure.repository;

import com.bsc.stokoin.category.domain.Category;
import com.bsc.stokoin.category.domain.enums.CategoryEnums;
import com.bsc.stokoin.category.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {
    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public List<Category> findAll() {
        return categoryJpaRepository.findAll();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryJpaRepository.findById(id);
    }

    @Override
    public Category save(Category category) {
        return categoryJpaRepository.save(category);
    }

    @Override
    public void deleteById(Long id) {
        categoryJpaRepository.deleteById(id);
    }

    @Override
    public Optional<Category> findByCategoryCode(CategoryEnums categoryCode) {
        return categoryJpaRepository.findByCategoryCode(categoryCode);
    }
}
