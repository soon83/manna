package com.sss.domain.category;

import java.util.List;

public interface CategoryService {

    List<CategoryInfo.Main> retrieveCategories();
    String registerCategory(CategoryCommand.RegisterCategory registerCategoryCommand);
    CategoryInfo.Main retrieveCategory(String categoryToken);
    void changeCategory(CategoryCommand.ChangeCategory changeCategoryCommand, String categoryToken);
    void deleteCategory(String categoryToken);
}
