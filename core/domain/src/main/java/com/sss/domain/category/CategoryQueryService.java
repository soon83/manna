package com.sss.domain.category;

import com.sss.domain.category.item.CategoryItem;

import java.util.List;

public interface CategoryQueryService {
    List<Category> readCategoryList();
    Category readCategory(String memberToken);
    List<CategoryQuery.Main> categorySeriesMapper(List<Category> categoryList);
    List<CategoryQuery.CategoryItemInfo> categoryItemSeriesMapper(List<CategoryItem> categoryItemList);
}
