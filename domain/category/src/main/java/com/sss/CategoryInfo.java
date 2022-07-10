package com.sss;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class CategoryInfo {

    @Getter
    @ToString
    public static class Main {
        private final Long id;
        private final String token;
        private final String title;

        @Builder
        public Main(
                Long id,
                String token,
                String title
        ) {
            this.id = id;
            this.token = token;
            this.title = title;
        }

        public Main(Category entity) {
            this.id = entity.getId();
            this.token = entity.getToken();
            this.title = entity.getTitle();
        }
    }
}
