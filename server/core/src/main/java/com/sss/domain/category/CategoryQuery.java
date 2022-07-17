package com.sss.domain.category;

import com.sss.domain.category.item.CategoryItem;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryQuery {

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
                    .collect(Collectors.toList()); // TODO 이거 infrastructure 로 빼야함,, 구현코드는 모두 추상화,,
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
