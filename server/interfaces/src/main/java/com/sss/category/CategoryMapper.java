package com.sss.category;

import com.sss.domain.category.CategoryQuery;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    public List<CategoryDto.MainResponse> categoryInfoListMapper(List<CategoryQuery.Main> infoList) {
        return infoList.stream()
                .map(categoryInfo -> {
                    var categoryItemInfoList = categoryInfo.getCategoryItemInfoList();
                    var categoryItemResponseList = categoryItemInfoListMapper(categoryItemInfoList);
                    return new CategoryDto.MainResponse(categoryInfo, categoryItemResponseList);
                })
                .collect(Collectors.toList());
    }

    public List<CategoryDto.CategoryItemResponse> categoryItemInfoListMapper(List<CategoryQuery.CategoryItemInfo> infoList) {
         return infoList.stream()
                .map(CategoryDto.CategoryItemResponse::new)
                .collect(Collectors.toList());
    }
}
