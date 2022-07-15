package com.sss.category;

import com.sss.domain.category.CategoryCommand;
import com.sss.domain.category.CategoryInfo;
import lombok.*;

import javax.validation.constraints.NotBlank;

public class CategoryDto {

    /**
     * request
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterRequest {

        @NotBlank(message = "categoryTitle 는 필수값입니다.")
        private String categoryTitle;


        public CategoryCommand.RegisterCategory toRegisterCategoryCommand() {
            return CategoryCommand.RegisterCategory.builder()
                    .title(categoryTitle)
                    .build();
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChangeRequest {
        @NotBlank(message = "categoryTitle 는 필수값입니다.")
        private String categoryTitle;

        public CategoryCommand.ChangeCategory toChangeCategoryCommand() {
            return CategoryCommand.ChangeCategory.builder()
                    .title(categoryTitle)
                    .build();
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChangeCategoryStatusRequest {

        @NotBlank(message = "categoryToken 는 필수값입니다.")
        private String categoryToken;
    }

    /**
     * response
     */
    @Getter
    @ToString
    public static class MainResponse {

        private final String categoryToken;
        private final String categoryTitle;

        public MainResponse(CategoryInfo.Main categoryInfo) {
            this.categoryToken = categoryInfo.getToken();
            this.categoryTitle = categoryInfo.getTitle();
        }
    }

    @Getter
    @Builder
    @ToString
    public static class RegisterResponse {

        private final String categoryToken;

        public RegisterResponse(String categoryToken) {
            this.categoryToken = categoryToken;
        }
    }
}
