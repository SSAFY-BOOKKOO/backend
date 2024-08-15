package com.ssafy.bookkoo.bookservice.controller;

import com.ssafy.bookkoo.bookservice.dto.category.CategoryDto;
import com.ssafy.bookkoo.bookservice.dto.category.CategorySearchParam;
import com.ssafy.bookkoo.bookservice.service.category.CategoryService;
import com.ssafy.bookkoo.bookservice.util.CategoryDatabaseInitializer;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryDatabaseInitializer categoryDatabaseInitializer;

    @PostMapping("/search")
    @Operation(
        summary = "카테고리 동적 검색 조회 API(파라미터 비어있을 시 전체 반환)",
        description = """
            카테고리 동적 검색 조회 API<b>(파라미터 비어있을 시 전체 반환)</b>



            <b>Input</b>:
            | Name | Type  | Description |
            |-----|-----|-------|
            | field | string | id 또는 name |
            | values | List(string) | 값 리스트 ex) ["1","2","3"] |

            <b>Output</b>:
            <br>
                type: _description_

            | Var | Type | Description |
            |-----|-----|-------|
            |  |  |  |
            """
    )
    public ResponseEntity<List<CategoryDto>> getAllCategories(
        @RequestBody(required = false) CategorySearchParam params
    ) {
        if (params == null) {

            return ResponseEntity.ok()
                                 .body(categoryService.getAllCategories());
        }
        return ResponseEntity.ok()
                             .body(categoryService.getCategoriesByFilter(params));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer categoryId) {
        return ResponseEntity.ok()
                             .body(categoryService.getCategory(categoryId));
    }

    @PostMapping("/init")
    public ResponseEntity<Boolean> initCategoryDB() {
        // 막아놓기
//        categoryDatabaseInitializer.init();
        return ResponseEntity.ok()
                             .body(true);
    }
}
