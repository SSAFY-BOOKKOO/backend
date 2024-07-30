package com.ssafy.bookkoo.bookservice.controller;

import com.ssafy.bookkoo.bookservice.dto.CategoryDto;
import com.ssafy.bookkoo.bookservice.service.category.CategoryService;
import com.ssafy.bookkoo.bookservice.util.CategoryDatabaseInitializer;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryDatabaseInitializer categoryDatabaseInitializer;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.ok()
                             .body(categoryService.getAllCategories());
    }

    @GetMapping("/by-ids")
    public ResponseEntity<List<CategoryDto>> getCategoriesById(@RequestParam List<Integer> ids) {
        return ResponseEntity.ok()
                             .body(categoryService.getCategoriesById(ids));
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestParam String name) {
        return ResponseEntity.ok()
                             .body(categoryService.addCategory(name));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer categoryId) {
        return ResponseEntity.ok()
                             .body(categoryService.getCategory(categoryId));
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> putCategory(
        @PathVariable Integer categoryId,
        @RequestParam String name
    ) {
        return ResponseEntity.ok()
                             .body(categoryService.updateCategory(categoryId, name));
    }

    @PostMapping("/init")
    public ResponseEntity<Boolean> initCategoryDB() {
        categoryDatabaseInitializer.init();
        return ResponseEntity.ok()
                             .body(true);
    }
}
