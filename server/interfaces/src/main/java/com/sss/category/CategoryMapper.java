package com.sss.category;

import com.sss.domain.category.CategoryQuery;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    public List<CategoryDto.MainResponse> categoryListInfoMapper(List<CategoryQuery.Main> categoryInfoList) {
        return categoryInfoList.stream()
                .map(categoryInfo -> {
                    var categoryItemInfoList = categoryInfo.getCategoryItemInfoList();
                    var categoryItemResponseList = categoryItemInfoListMapper(categoryItemInfoList);
                    return new CategoryDto.MainResponse(categoryInfo, categoryItemResponseList);
                })
                .collect(Collectors.toList());
    }

    public List<CategoryDto.CategoryItemResponse> categoryItemInfoListMapper(List<CategoryQuery.CategoryItemInfo> categoryItemInfoList) {
         return categoryItemInfoList.stream()
                .map(CategoryDto.CategoryItemResponse::new)
                .collect(Collectors.toList());
    }
}
