package com.sss.domain.category;

import com.sss.domain.category.item.CategoryItem;

import java.util.List;

public interface CategoryQueryService {
    List<Category> readCategoryList();
    List<CategoryItem> readCategoryItemListById(List<Long> categoryItemIdList);
    Category readCategory(String memberToken);
    List<CategoryQuery.Main> categoryListMapper(List<Category> categoryList);
    List<CategoryQuery.CategoryItemInfo> categoryItemListMapper(List<CategoryItem> categoryItemList);
}
