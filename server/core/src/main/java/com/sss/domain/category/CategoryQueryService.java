package com.sss.domain.category;

import java.util.List;

public interface CategoryQueryService {
    List<Category> getCategoryList();
    Category getCategory(String memberToken);
}
