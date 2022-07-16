package com.sss.domain.category;

import com.sss.domain.category.item.CategoryItem;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryInfo {

    @Getter
    @ToString
    public static class Main {
        private final String token;
        private final String title;
        private final Integer ordering;
        private final List<CategoryItemInfo> categoryItemInfoList;

        public Main(Category entity) {
            this.token = entity.getToken();
            this.title = entity.getTitle();
            this.ordering = entity.getOrdering();
            this.categoryItemInfoList = entity.getCategoryItemList().stream()
                    .map(CategoryItemInfo::new)
                    .collect(Collectors.toList()); // TODO 이거 밖으로 빼야하나,,
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
