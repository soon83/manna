package com.sss.category;

import com.sss.response.Res;
import com.sss.util.UriGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.stream.Collectors;

@Slf4j
@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/v1/category-list")
@RequiredArgsConstructor
public class CategoryApiController {
    private final CategoryFacade categoryFacade;

    /**
     * 카테고리 목록 조회
     * @return
     */
    @GetMapping
    public ResponseEntity<Res> fetchCategoryList() {
        var categoryInfoList = categoryFacade.fetchCategoryList();
        var response = categoryInfoList.stream()
                .map(CategoryDto.MainResponse::new)
                .collect(Collectors.toList()); // TODO 이거 infrastructure 로 빼야함,, 구현코드는 모두 추상화하자
        return ResponseEntity.status(HttpStatus.OK).body(Res.success(response));
    }

    /**
     * 카테고리 단건 조회
     * @param categoryToken
     * @return
     */
    @GetMapping("/{categoryToken}")
    public ResponseEntity<Res> fetchCategory(@PathVariable String categoryToken) {
        var categoryInfo = categoryFacade.fetchCategory(categoryToken);
        var response = new CategoryDto.MainResponse(categoryInfo);
        return ResponseEntity.status(HttpStatus.OK).body(Res.success(response));
    }

    /**
     * 카테고리 단건 등록
     * @param request
     * @return
     * @throws URISyntaxException
     */
    @PostMapping
    public ResponseEntity<Res> createCategory(@RequestBody @Valid CategoryDto.CreateRequest request) throws URISyntaxException {
        var createCategoryCommand = request.toCreateCategoryCommand();
        var categoryToken = categoryFacade.createCategory(createCategoryCommand);
        var response = new CategoryDto.CreateResponse(categoryToken);
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(UriGenerator.getLocation(response.getCategoryToken()))
                .body(Res.success(response));
    }

    /**
     * 카테고리 단건 수정
     * @param categoryToken
     * @param request
     * @return
     */
    @PutMapping("/{categoryToken}")
    public ResponseEntity<Res> updateCategory(@PathVariable String categoryToken, @RequestBody @Valid CategoryDto.UpdateRequest request) {
        var updateCategoryCommand = request.toUpdateCategoryCommand();
        categoryFacade.updateCategory(updateCategoryCommand, categoryToken);
        return ResponseEntity.status(HttpStatus.OK).body(Res.success());
    }

    /**
     * 카테고리 단건 삭제
     * @param categoryToken
     * @return
     */
    @DeleteMapping("/{categoryToken}")
    public ResponseEntity<Res> deleteCategory(@PathVariable String categoryToken) {
        categoryFacade.deleteCategory(categoryToken);
        return ResponseEntity.status(HttpStatus.OK).body(Res.success());
    }
}
