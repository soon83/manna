package com.sss.infrastructure.category;

import com.sss.domain.category.Category;
import com.sss.domain.category.CategoryQuery;
import com.sss.domain.category.CategoryQueryService;
import com.sss.domain.category.item.CategoryItem;
import com.sss.exception.category.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class CategoryQueryServiceImpl implements CategoryQueryService {
    private final CategoryRepository categoryRepository;
    private final CategoryItemRepository categoryItemRepository;

    @Override
    public List<Category> readCategoryList() {
        return categoryRepository.findAll();
    }

    @Override
    public List<CategoryItem> readCategoryItemListById(List<Long> categoryItemIdList) {
        return categoryItemRepository.findAllById(categoryItemIdList);
    }

    @Override
    public Category readCategory(String categoryToken) {
        return categoryRepository.findByToken(categoryToken)
                .orElseThrow(CategoryNotFoundException::new);
    }

    @Override
    public List<CategoryQuery.Main> categoryListMapper(List<Category> categoryList) {
        return categoryList.stream()
                .map(category -> {
                    var categoryItemList = category.getCategoryItemList();
                    var categoryItemInfoList = categoryItemListMapper(categoryItemList);
                    return new CategoryQuery.Main(category, categoryItemInfoList);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryQuery.CategoryItemInfo> categoryItemListMapper(List<CategoryItem> categoryItemList) {
        return categoryItemList.stream()
                .map(CategoryQuery.CategoryItemInfo::new)
                .collect(Collectors.toList());
    }
}
