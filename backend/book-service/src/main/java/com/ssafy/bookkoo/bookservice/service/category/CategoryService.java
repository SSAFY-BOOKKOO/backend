package com.ssafy.bookkoo.bookservice.service.category;

import com.ssafy.bookkoo.bookservice.dto.category.CategoryDto;
import com.ssafy.bookkoo.bookservice.dto.category.CategorySearchParam;
import java.util.List;

/**
 * 카테고리 서비스 인터페이스로, 카테고리 관련 비즈니스 로직을 정의합니다.
 */
public interface CategoryService {

    /**
     * 모든 카테고리를 조회합니다.
     *
     * @return 모든 카테고리의 DTO 리스트
     */
    public List<CategoryDto> getAllCategories();

    /**
     * 주어진 카테고리 ID로 카테고리를 조회합니다.
     *
     * @param categoryId 카테고리 ID
     * @return 조회된 카테고리 DTO
     */
    public CategoryDto getCategory(Integer categoryId);

    /**
     * 새로운 카테고리를 추가합니다.
     *
     * @param name 카테고리 이름
     * @return 생성된 카테고리 DTO
     */
    public CategoryDto addCategory(String name);

    /**
     * 주어진 카테고리 ID와 이름으로 카테고리를 업데이트합니다.
     *
     * @param categoryId 카테고리 ID
     * @param name       카테고리 이름
     * @return 업데이트된 카테고리 DTO
     */
    public CategoryDto updateCategory(
        Integer categoryId,
        String name
    );

    /**
     * 주어진 필터 조건을 기반으로 카테고리 목록을 조회합니다.
     *
     * @param params 카테고리 검색 파라미터
     * @return 필터 조건에 맞는 카테고리 DTO 리스트
     */
    List<CategoryDto> getCategoriesByFilter(CategorySearchParam params);
}
