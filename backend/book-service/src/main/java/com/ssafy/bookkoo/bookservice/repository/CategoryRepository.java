package com.ssafy.bookkoo.bookservice.repository;

import com.ssafy.bookkoo.bookservice.entity.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer>,
    CategoryCustomRepository {

    Category findByName(String name);

    List<Category> findByIdIn(List<Integer> ids);
}
