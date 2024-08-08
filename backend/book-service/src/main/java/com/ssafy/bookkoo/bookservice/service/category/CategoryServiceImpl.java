package com.ssafy.bookkoo.bookservice.service.category;

import com.ssafy.bookkoo.bookservice.dto.category.CategoryDto;
import com.ssafy.bookkoo.bookservice.dto.category.CategorySearchParam;
import com.ssafy.bookkoo.bookservice.entity.Category;
import com.ssafy.bookkoo.bookservice.exception.CategoryNotFoundException;
import com.ssafy.bookkoo.bookservice.mapper.CategoryMapper;
import com.ssafy.bookkoo.bookservice.repository.category.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * CategoryService의 구현체로, 카테고리 관련 비즈니스 로직을 처리합니다.
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    /**
     * 모든 카테고리를 조회합니다.
     *
     * @return 모든 카테고리의 DTO 리스트
     */
    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getAllCategories() {
        return categoryMapper.toResponseDtoList(categoryRepository.findAll());
    }

    /**
     * 주어진 카테고리 ID로 카테고리를 조회합니다.
     *
     * @param categoryId 카테고리 ID
     * @return 조회된 카테고리 DTO
     * @throws CategoryNotFoundException 주어진 ID에 해당하는 카테고리가 없을 때 발생
     */
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

    /**
     * 새로운 카테고리를 추가합니다.
     *
     * @param name 카테고리 이름
     * @return 생성된 카테고리 DTO
     */
    @Override
    @Transactional
    public CategoryDto addCategory(String name) {
        Category category = Category.builder()
                                    .name(name)
                                    .build();
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    /**
     * 주어진 카테고리 ID와 이름으로 카테고리를 업데이트합니다.
     *
     * @param categoryId 카테고리 ID
     * @param name       카테고리 이름
     * @return 업데이트된 카테고리 DTO
     * @throws CategoryNotFoundException 주어진 ID에 해당하는 카테고리가 없을 때 발생
     */
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

    /**
     * 주어진 필터 조건을 기반으로 카테고리 목록을 조회합니다.
     *
     * @param params 카테고리 검색 파라미터
     * @return 필터 조건에 맞는 카테고리 DTO 리스트
     */
    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getCategoriesByFilter(CategorySearchParam params) {
        return categoryMapper.toResponseDtoList(categoryRepository.findByFilter(params));
    }
}
