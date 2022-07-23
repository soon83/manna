package com.sss.domain.category;

import com.sss.domain.category.item.CategoryItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

public class CategoryQuery {

    @Getter
    @ToString
    public static class Main {
        private final Long id;
        private final String token;
        private final String title;
        private final Integer ordering;
        private final List<CategoryItemInfo> categoryItemInfoList;

        public Main(Category entity, List<CategoryItemInfo> categoryItemInfoList) {
            this.id = entity.getId();
            this.token = entity.getToken();
            this.title = entity.getTitle();
            this.ordering = entity.getOrdering();
            this.categoryItemInfoList = categoryItemInfoList;
        }
    }

    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    public static class CategoryItemInfo {
        private final Long id;
        private final String token;
        private final String title;
        private final Integer ordering;
        private final Long categoryId;
        private final String categoryToken;

        public CategoryItemInfo(CategoryItem entity) {
            this.id = entity.getId();
            this.token = entity.getToken();
            this.title = entity.getTitle();
            this.ordering = entity.getOrdering();
            this.categoryId = entity.getCategory().getId();
            this.categoryToken = entity.getCategory().getToken();
        }
    }
}
