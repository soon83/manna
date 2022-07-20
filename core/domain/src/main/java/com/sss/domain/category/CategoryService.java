package com.sss.domain.category;

import java.util.List;

public interface CategoryService {
    List<CategoryQuery.Main> fetchCategoryList();
    CategoryQuery.Main fetchCategory(String categoryToken);
    String registerCategory(CategoryCommand.CreateCategory command);
    void modifyCategory(CategoryCommand.UpdateCategory command, String categoryToken);
    void removeCategory(String categoryToken);
}
