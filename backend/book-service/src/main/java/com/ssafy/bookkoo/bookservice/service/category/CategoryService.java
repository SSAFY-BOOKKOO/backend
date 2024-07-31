package com.ssafy.bookkoo.bookservice.service.category;

import com.ssafy.bookkoo.bookservice.dto.category.CategoryDto;
import com.ssafy.bookkoo.bookservice.dto.category.CategorySearchParam;
import java.util.List;

public interface CategoryService {

    public List<CategoryDto> getAllCategories();

    public CategoryDto getCategory(Integer categoryId);

    public CategoryDto addCategory(String name);

    public CategoryDto updateCategory(
        Integer categoryId,
        String name
    );

    List<CategoryDto> getCategoriesByFilter(CategorySearchParam params);
}
