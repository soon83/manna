package com.sss;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryFacade {

    private final CategoryService categoryService;

    public List<CategoryInfo.Main> retrieveCategories() {
        return categoryService.retrieveCategories();
    }

    public CategoryInfo.Main retrieveCategory(String categoryToken) {
        return categoryService.retrieveCategory(categoryToken);
    }

    public String registerCategory(CategoryCommand.RegisterCategory registerCategoryCommand) {
        return categoryService.registerCategory(registerCategoryCommand);
    }

    public void changeCategory(CategoryCommand.ChangeCategory changeCategoryCommand, String categoryToken) {
        categoryService.changeCategory(changeCategoryCommand, categoryToken);
    }

    public void deleteCategory(String categoryToken) {
        categoryService.deleteCategory(categoryToken);
    }
}
