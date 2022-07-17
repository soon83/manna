package com.sss.domain.category;

import java.util.List;

public interface CategoryService {
    List<CategoryInfo.Main> fetchCategoryList();
    CategoryInfo.Main fetchCategory(String categoryToken);
    String createCategory(CategoryCommand.CreateCategory createCategoryCommand);
    void updateCategory(CategoryCommand.UpdateCategory updateCategoryCommand, String categoryToken);
    void deleteCategory(String categoryToken);
}
