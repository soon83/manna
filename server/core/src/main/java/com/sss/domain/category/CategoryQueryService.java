package com.sss.domain.category;

import java.util.List;

public interface CategoryQueryService {
    List<Category> readCategoryList();
    Category readCategory(String memberToken);
}
