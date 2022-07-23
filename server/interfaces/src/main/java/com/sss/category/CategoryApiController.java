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

@Slf4j
@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/v1/category-list")
@RequiredArgsConstructor
public class CategoryApiController {
    private final CategoryFacade categoryFacade;
    private final CategoryMapper categoryMapper;

    /**
     * 카테고리 목록 조회
     * @return
     */
    @GetMapping
    public ResponseEntity<Res> fetchCategoryList() {
        var categoryInfoList = categoryFacade.fetchCategoryList();
        var response = categoryMapper.categoryInfoListMapper(categoryInfoList); // TODO 이렇게 하는게 맞나 싶다,,
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
        var categoryItemInfoList = categoryInfo.getCategoryItemInfoList();
        var response = categoryMapper.categoryItemInfoListMapper(categoryItemInfoList); // TODO 이렇게 하는게 맞나 싶다,,
        return ResponseEntity.status(HttpStatus.OK).body(Res.success(response));
    }

    /**
     * 카테고리 단건 등록
     * @param request
     * @return
     * @throws URISyntaxException
     */
    @PostMapping
    public ResponseEntity<Res> registerCategory(@RequestBody @Valid CategoryDto.RegisterRequest request) throws URISyntaxException {
        var command = request.toCreateCategoryCommand();
        var categoryToken = categoryFacade.registerCategory(command);
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
    public ResponseEntity<Res> modifyCategory(@PathVariable String categoryToken, @RequestBody @Valid CategoryDto.ModifyRequest request) {
        var command = request.toUpdateCategoryCommand();
        categoryFacade.modifyCategory(command, categoryToken);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Res.success());
    }

    /**
     * 카테고리 단건 삭제
     * @param categoryToken
     * @return
     */
    @DeleteMapping("/{categoryToken}")
    public ResponseEntity<Res> removeCategory(@PathVariable String categoryToken) {
        categoryFacade.removeCategory(categoryToken);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Res.success());
    }
}
