package com.sss.category;

import com.sss.domain.category.CategoryCommand;
import com.sss.domain.category.CategoryQuery;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

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

        public CategoryCommand.CreateCategory toCreateCategoryCommand() {
            return CategoryCommand.CreateCategory.builder()
                    .title(categoryTitle)
                    .build();
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ModifyRequest {
        @NotBlank(message = "categoryTitle 는 필수값입니다.")
        private String categoryTitle;
        @NotNull(message = "categoryOrdering 는 필수값입니다.")
        private Integer categoryOrdering;

        public CategoryCommand.UpdateCategory toUpdateCategoryCommand() {
            return CategoryCommand.UpdateCategory.builder()
                    .title(categoryTitle)
                    .ordering(categoryOrdering)
                    .build();
        }
    }

    /**
     * response
     */
    @Getter
    @ToString
    public static class MainResponse {
        private final String categoryToken;
        private final String categoryTitle;
        private final Integer categoryOrdering;
        private final List<CategoryItemResponse> categoryItemList;

        public MainResponse(CategoryQuery.Main categoryInfo, List<CategoryItemResponse> categoryItemList) {
            this.categoryToken = categoryInfo.getToken();
            this.categoryTitle = categoryInfo.getTitle();
            this.categoryOrdering = categoryInfo.getOrdering();
            this.categoryItemList = categoryItemList;
        }
    }

    @Getter
    @Builder
    @ToString
    public static class CreateResponse {
        private final String categoryToken;

        public CreateResponse(String categoryToken) {
            this.categoryToken = categoryToken;
        }
    }

    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    public static class CategoryItemResponse {
        private final String categoryItemToken;
        private final String categoryItemTitle;
        private final Integer categoryItemOrdering;
        private final String categoryToken;

        public CategoryItemResponse(CategoryQuery.CategoryItemInfo info) {
            this.categoryItemToken = info.getToken();
            this.categoryItemTitle = info.getTitle();
            this.categoryItemOrdering = info.getOrdering();
            this.categoryToken = info.getCategoryToken();
        }
    }
}
