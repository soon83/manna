package com.sss.domain.category;

import com.sss.domain.category.item.CategoryItem;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

public class CategoryQuery {

    @Getter
    @ToString
    public static class Main {
        private final String token;
        private final String title;
        private final Integer ordering;
        private final List<CategoryItemInfo> categoryItemInfoList;

        public Main(Category entity, List<CategoryItemInfo> categoryItemInfoList) {
            this.token = entity.getToken();
            this.title = entity.getTitle();
            this.ordering = entity.getOrdering();
            this.categoryItemInfoList = categoryItemInfoList;
        }
    }

    @Getter
    @ToString
    public static class CategoryItemInfo {
        private final String token;
        private final String title;
        private final Integer ordering;

        public CategoryItemInfo(CategoryItem entity) {
            this.token = entity.getToken();
            this.title = entity.getTitle();
            this.ordering = entity.getOrdering();
        }
    }
}
