package com.sss.infrastructure.category;

import com.sss.domain.category.item.CategoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryItemRepository extends JpaRepository<CategoryItem, Long> {
}
