package com.sss;

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
    public List<CategoryInfo.Main> retrieveCategories() {
        return categoryQueryService.getCategories().stream()
                .map(CategoryInfo.Main::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public String registerCategory(CategoryCommand.RegisterCategory registerCategoryCommand) {
        var member = registerCategoryCommand.toEntity();
        var createdCategory = categoryCommandService.save(member);
        return createdCategory.getToken();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryInfo.Main retrieveCategory(String memberToken) {
        var member = categoryQueryService.getCategory(memberToken);
        return new CategoryInfo.Main(member);
    }

    @Override
    @Transactional
    public void changeCategory(CategoryCommand.ChangeCategory changeCategoryCommand, String memberToken) {
        var member = categoryQueryService.getCategory(memberToken);
        member.updateCategory(
                changeCategoryCommand.getTitle()
        );
    }

    @Override
    @Transactional
    public void deleteCategory(String memberToken) {
        var member = categoryQueryService.getCategory(memberToken);
        categoryCommandService.delete(member);
    }
}
