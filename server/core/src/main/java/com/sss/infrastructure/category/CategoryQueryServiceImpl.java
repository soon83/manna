package com.sss.infrastructure.category;

import com.sss.exception.category.CategoryNotFoundException;
import com.sss.domain.category.Category;
import com.sss.domain.category.CategoryQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CategoryQueryServiceImpl implements CategoryQueryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> readCategoryList() {
        return categoryRepository.findAll();
    }

    @Override
    public Category readCategory(String categoryToken) {
        return categoryRepository.findByToken(categoryToken)
                .orElseThrow(CategoryNotFoundException::new);
    }
}
