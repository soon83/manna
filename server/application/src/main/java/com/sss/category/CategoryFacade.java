package com.sss.category;

import com.sss.domain.category.CategoryCommand;
import com.sss.domain.category.CategoryInfo;
import com.sss.domain.category.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryFacade {

    private final CategoryService categoryService;

    public List<CategoryInfo.Main> fetchCategoryList() {
        return categoryService.fetchCategoryList();
    }

    public CategoryInfo.Main fetchCategory(String categoryToken) {
        return categoryService.fetchCategory(categoryToken);
    }

    public String createCategory(CategoryCommand.CreateCategory createCategoryCommand) {
        return categoryService.createCategory(createCategoryCommand);
    }

    public void updateCategory(CategoryCommand.UpdateCategory updateCategoryCommand, String categoryToken) {
        categoryService.updateCategory(updateCategoryCommand, categoryToken);
    }

    public void deleteCategory(String categoryToken) {
        categoryService.deleteCategory(categoryToken);
    }
}
