package com.sss.domain.category;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class CategoryCommand {

    @Getter
    @Builder
    @ToString
    public static class CreateCategory {
        private String title;
        private Integer ordering;

        public Category toEntity() {
            return Category.builder()
                    .title(title)
                    .ordering(ordering)
                    .build();
        }
    }

    @Getter
    @Builder
    @ToString
    public static class UpdateCategory {
        private String title;
        private Integer ordering;
    }
}
