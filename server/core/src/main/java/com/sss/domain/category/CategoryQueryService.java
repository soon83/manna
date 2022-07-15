package com.sss.domain.category;

import java.util.List;

public interface CategoryQueryService {

    List<Category> getCategories();
    Category getCategory(String memberToken);

}
