package com.sss;

import java.util.List;

public interface CategoryQueryService {

    List<Category> getCategories();
    Category getCategory(String memberToken);

}
