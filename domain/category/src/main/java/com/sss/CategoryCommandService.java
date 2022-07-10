package com.sss;

import com.sss.item.CategoryItem;

public interface CategoryCommandService {

    Category save(Category category);
    CategoryItem save(CategoryItem categoryItem);
    void delete(Category category);
    void delete(CategoryItem categoryItem);
}
