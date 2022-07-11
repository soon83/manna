package com.sss.domain.category;

import com.sss.domain.category.item.CategoryItem;

public interface CategoryCommandService {

    Category save(Category category);
    CategoryItem save(CategoryItem categoryItem);
    void delete(Category category);
    void delete(CategoryItem categoryItem);
}
