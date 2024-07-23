package com.ssafy.bookkoo.bookservice.service.category;

import com.ssafy.bookkoo.bookservice.entity.Category;
import com.ssafy.bookkoo.bookservice.exception.CategoryNotFoundException;
import com.ssafy.bookkoo.bookservice.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    @Transactional
    public Category getCategory(Integer categoryId) {
        return categoryRepository.findById(categoryId)
                                 .orElseThrow(
                                     () -> new CategoryNotFoundException(
                                         "There is no category for id : " + categoryId)
                                 );
    }

    @Override
    @Transactional
    public Category addCategory(String name) {
        Category category = Category.builder()
                                    .name(name)
                                    .build();
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public Category updateCategory(
        Integer categoryId,
        String name
    ) {
        Category category = getCategory(categoryId);
        category.setName(name);

        categoryRepository.save(category);
        return category;
    }
}
