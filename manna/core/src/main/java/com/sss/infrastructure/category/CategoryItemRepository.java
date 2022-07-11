package com.sss.infrastructure.category;

import com.sss.domain.category.item.CategoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryItemRepository extends JpaRepository<CategoryItem, Long> {

    Optional<CategoryItem> findByToken(String categoryItemToken);
}
