package com.sss.domain.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryQueryService categoryQueryService;
    private final CategoryCommandService categoryCommandService;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryInfo.Main> fetchCategoryList() {
        var categoryList = categoryQueryService.getCategoryList();
        return categoryList.stream()
                .map(CategoryInfo.Main::new)
                .collect(Collectors.toList()); // TODO 이거 infrastructure 로 빼야함,, 구현코드는 모두 추상화하자
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryInfo.Main fetchCategory(String memberToken) {
        var member = categoryQueryService.getCategory(memberToken);
        return new CategoryInfo.Main(member);
    }

    @Override
    @Transactional
    public String createCategory(CategoryCommand.CreateCategory createCategoryCommand) {
        var member = createCategoryCommand.toEntity();
        var createdCategory = categoryCommandService.save(member);
        return createdCategory.getToken();
    }

    @Override
    @Transactional
    public void updateCategory(CategoryCommand.UpdateCategory updateCategoryCommand, String memberToken) {
        var member = categoryQueryService.getCategory(memberToken);
        member.updateCategory(
                updateCategoryCommand.getTitle(),
                updateCategoryCommand.getOrdering()
        );
    }

    @Override
    @Transactional
    public void deleteCategory(String memberToken) {
        var member = categoryQueryService.getCategory(memberToken);
        categoryCommandService.delete(member);
    }
}
