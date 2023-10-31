package com.bsc.stokoin.category.infrastructure.repository;

import com.bsc.stokoin.category.domain.Category;
import com.bsc.stokoin.category.domain.enums.CategoryEnums;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryJpaRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCategoryCode(CategoryEnums categoryCode);
}
