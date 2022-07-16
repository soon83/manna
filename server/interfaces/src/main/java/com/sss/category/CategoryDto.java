package com.sss.category;

import com.sss.domain.category.CategoryCommand;
import com.sss.domain.category.CategoryInfo;
import com.sss.domain.category.item.CategoryItem;
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

        @NotNull(message = "categoryOrdering 는 필수값입니다.")
        private Integer categoryOrdering;

        public CategoryCommand.ChangeCategory toChangeCategoryCommand() {
            return CategoryCommand.ChangeCategory.builder()
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

        public MainResponse(CategoryInfo.Main categoryInfo) {
            this.categoryToken = categoryInfo.getToken();
            this.categoryTitle = categoryInfo.getTitle();
            this.categoryOrdering = categoryInfo.getOrdering();
            this.categoryItemList = categoryInfo.getCategoryItemInfoList().stream()
                    .map(CategoryItemResponse::new)
                    .collect(Collectors.toList()); // TODO 이거 infrastructure 로 빼야함,, 구현코드는 모두 추상화하자
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

    @Getter
    @ToString
    public static class CategoryItemResponse {
        private final String categoryItemToken;
        private final String categoryItemTitle;
        private final Integer categoryItemOrdering;

        public CategoryItemResponse(CategoryInfo.CategoryItemInfo categoryItemInfo) {
            this.categoryItemToken = categoryItemInfo.getToken();
            this.categoryItemTitle = categoryItemInfo.getTitle();
            this.categoryItemOrdering = categoryItemInfo.getOrdering();
        }
    }
}
