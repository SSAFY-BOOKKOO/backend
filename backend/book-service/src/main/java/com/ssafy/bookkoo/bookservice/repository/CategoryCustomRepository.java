package com.ssafy.bookkoo.bookservice.repository;

import com.ssafy.bookkoo.bookservice.dto.CategorySearchParam;
import com.ssafy.bookkoo.bookservice.entity.Category;
import java.util.List;

public interface CategoryCustomRepository {

    List<Category> findByFilter(CategorySearchParam searchParam);
}
