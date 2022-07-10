package com.sss.infrastructure;

import com.sss.Category;
import com.sss.CategoryCommandService;
import com.sss.item.CategoryItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CategoryCommandServiceImpl implements CategoryCommandService {

    private final CategoryRepository categoryRepository;
    private final CategoryItemRepository categoryItemRepository;

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public CategoryItem save(CategoryItem categoryItem) {
        return categoryItemRepository.save(categoryItem);
    }

    @Override
    public void delete(Category category) {
        categoryRepository.delete(category);
    }

    @Override
    public void delete(CategoryItem categoryItem) {
        categoryItemRepository.delete(categoryItem);
    }
}
