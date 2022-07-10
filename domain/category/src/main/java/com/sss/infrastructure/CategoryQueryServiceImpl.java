package com.sss.infrastructure;

import com.sss.Category;
import com.sss.CategoryNotFoundException;
import com.sss.CategoryQueryService;
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
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategory(String categoryToken) {
        return categoryRepository.findByToken(categoryToken)
                .orElseThrow(CategoryNotFoundException::new);
    }
}
