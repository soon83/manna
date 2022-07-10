package com.sss;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class CategoryCommand {

    @Getter
    @Builder
    @ToString
    public static class RegisterCategory {

        private String title;

        public Category toEntity() {
            return Category.builder()
                    .title(title)
                    .build();
        }
    }

    @Getter
    @Builder
    @ToString
    public static class ChangeCategory {
        private String title;
    }
}
