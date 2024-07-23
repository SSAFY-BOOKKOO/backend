package com.ssafy.bookkoo.bookservice.service.category;

import com.ssafy.bookkoo.bookservice.entity.Category;
import java.util.List;

public interface CategoryService {

    public List<Category> getAllCategories();

    public Category getCategory(Integer categoryId);

    public Category addCategory(String name);

    public Category updateCategory(
        Integer categoryId,
        String name
    );
}
