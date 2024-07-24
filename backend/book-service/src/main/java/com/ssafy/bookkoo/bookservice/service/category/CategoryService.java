package com.ssafy.bookkoo.bookservice.service.category;

import com.ssafy.bookkoo.bookservice.dto.CategoryDto;
import java.util.List;

public interface CategoryService {

    public List<CategoryDto> getAllCategories();

    public CategoryDto getCategory(Integer categoryId);

    public CategoryDto addCategory(String name);

    public CategoryDto updateCategory(
        Integer categoryId,
        String name
    );
}
