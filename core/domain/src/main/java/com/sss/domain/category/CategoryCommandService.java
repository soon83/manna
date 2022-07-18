package com.sss.domain.category;

import com.sss.domain.category.item.CategoryItem;

public interface CategoryCommandService {
    Category create(Category category);
    CategoryItem create(CategoryItem categoryItem);
    void delete(Category category);
    void delete(CategoryItem categoryItem);
}
