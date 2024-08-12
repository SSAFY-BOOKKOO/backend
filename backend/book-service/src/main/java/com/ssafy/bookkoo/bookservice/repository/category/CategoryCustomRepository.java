package com.ssafy.bookkoo.bookservice.repository.category;

import com.ssafy.bookkoo.bookservice.dto.category.CategorySearchParam;
import com.ssafy.bookkoo.bookservice.entity.Category;
import java.util.List;

/**
 * 커스텀 카테고리 레포지토리 인터페이스로, 특정 필터 조건을 기반으로 카테고리를 검색하는 메서드를 정의합니다.
 */
public interface CategoryCustomRepository {

    /**
     * 주어진 검색 파라미터를 기반으로 카테고리를 검색합니다.
     *
     * @param searchParam 카테고리 검색 파라미터
     * @return 검색된 카테고리 리스트
     */
    List<Category> findByFilter(CategorySearchParam searchParam);
}
