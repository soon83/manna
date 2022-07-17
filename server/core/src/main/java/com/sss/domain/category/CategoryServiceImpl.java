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
    public List<CategoryQuery.Main> fetchCategoryList() {
        var categoryList = categoryQueryService.readCategoryList();
        return categoryList.stream()
                .map(CategoryQuery.Main::new)
                .collect(Collectors.toList()); // TODO 이거 infrastructure 로 빼야함,, 구현코드는 모두 추상화,,
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryQuery.Main fetchCategory(String memberToken) {
        var member = categoryQueryService.readCategory(memberToken);
        return new CategoryQuery.Main(member);
    }

    @Override
    @Transactional
    public String registerCategory(CategoryCommand.CreateCategory createCategoryCommand) {
        var member = createCategoryCommand.toEntity();
        var createdCategory = categoryCommandService.create(member);
        return createdCategory.getToken();
    }

    @Override
    @Transactional
    public void modifyCategory(CategoryCommand.UpdateCategory updateCategoryCommand, String memberToken) {
        var member = categoryQueryService.readCategory(memberToken);
        member.updateCategory(
                updateCategoryCommand.getTitle(),
                updateCategoryCommand.getOrdering()
        );
    }

    @Override
    @Transactional
    public void removeCategory(String memberToken) {
        var member = categoryQueryService.readCategory(memberToken);
        categoryCommandService.delete(member);
    }
}
