package com.sss.category;

import com.sss.domain.category.CategoryCommand;
import com.sss.domain.category.CategoryQuery;
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

    public List<CategoryQuery.Main> fetchCategoryList() {
        return categoryService.fetchCategoryList();
    }

    public CategoryQuery.Main fetchCategory(String categoryToken) {
        return categoryService.fetchCategory(categoryToken);
    }

    public String registerCategory(CategoryCommand.CreateCategory createCategoryCommand) {
        return categoryService.registerCategory(createCategoryCommand);
    }

    public void modifyCategory(CategoryCommand.UpdateCategory updateCategoryCommand, String categoryToken) {
        categoryService.modifyCategory(updateCategoryCommand, categoryToken);
    }

    public void removeCategory(String categoryToken) {
        categoryService.removeCategory(categoryToken);
    }
}
