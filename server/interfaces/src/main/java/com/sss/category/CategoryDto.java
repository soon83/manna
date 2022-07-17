package com.sss.category;

import com.sss.domain.category.CategoryCommand;
import com.sss.domain.category.CategoryQuery;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

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

        public MainResponse(CategoryQuery.Main categoryInfo) {
            this.categoryToken = categoryInfo.getToken();
            this.categoryTitle = categoryInfo.getTitle();
            this.categoryOrdering = categoryInfo.getOrdering();
            this.categoryItemList = categoryInfo.getCategoryItemInfoList().stream()
                    .map(CategoryItemResponse::new)
                    .collect(Collectors.toList()); // TODO 이거 infrastructure 로 빼야함,, 구현코드는 모두 추상화,,
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
    @ToString
    public static class CategoryItemResponse {
        private final String categoryItemToken;
        private final String categoryItemTitle;
        private final Integer categoryItemOrdering;

        public CategoryItemResponse(CategoryQuery.CategoryItemInfo categoryItemInfo) {
            this.categoryItemToken = categoryItemInfo.getToken();
            this.categoryItemTitle = categoryItemInfo.getTitle();
            this.categoryItemOrdering = categoryItemInfo.getOrdering();
        }
    }
}
