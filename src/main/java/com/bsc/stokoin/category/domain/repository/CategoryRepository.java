package com.bsc.stokoin.category.domain.repository;

import com.bsc.stokoin.category.domain.Category;
import com.bsc.stokoin.category.domain.enums.CategoryEnums;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    List<Category> findAll();
    Optional<Category> findById(Long id);
    Category save(Category category);
    void deleteById(Long id);

    Optional<Category> findByCategoryCode(CategoryEnums categoryCode);
}
