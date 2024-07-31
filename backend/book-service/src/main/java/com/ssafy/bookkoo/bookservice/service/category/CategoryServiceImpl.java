package com.ssafy.bookkoo.bookservice.service.category;

import com.ssafy.bookkoo.bookservice.dto.CategoryDto;
import com.ssafy.bookkoo.bookservice.dto.CategorySearchParam;
import com.ssafy.bookkoo.bookservice.entity.Category;
import com.ssafy.bookkoo.bookservice.exception.CategoryNotFoundException;
import com.ssafy.bookkoo.bookservice.mapper.CategoryMapper;
import com.ssafy.bookkoo.bookservice.repository.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getAllCategories() {
        return categoryMapper.toResponseDtoList(categoryRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto getCategory(Integer categoryId) {
        return categoryMapper.toDto(categoryRepository.findById(categoryId)
                                                      .orElseThrow(
                                                          () -> new CategoryNotFoundException(
                                                              "There is no category for id : "
                                                                  + categoryId)
                                                      ));
    }

    @Override
    @Transactional
    public CategoryDto addCategory(String name) {
        Category category = Category.builder()
                                    .name(name)
                                    .build();
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(
        Integer categoryId,
        String name
    ) {
        Category category = categoryRepository.findById(categoryId)
                                              .orElseThrow(
                                                  () -> new CategoryNotFoundException(
                                                      "There is no category for id : "
                                                          + categoryId)
                                              );
        category.setName(name);

        categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    @Override
    public List<CategoryDto> getCategoriesByFilter(CategorySearchParam params) {
        return categoryMapper.toResponseDtoList(categoryRepository.findByFilter(params));
    }
}
