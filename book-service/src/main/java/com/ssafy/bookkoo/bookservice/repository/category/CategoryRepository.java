package com.ssafy.bookkoo.bookservice.repository.category;

import com.ssafy.bookkoo.bookservice.entity.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Category 엔티티에 대한 기본 JPA 레포지토리와 커스텀 레포지토리를 결합한 인터페이스입니다.
 */
public interface CategoryRepository extends JpaRepository<Category, Integer>,
    CategoryCustomRepository {

    /**
     * 주어진 이름으로 카테고리를 검색합니다.
     *
     * @param name 카테고리 이름
     * @return 검색된 카테고리, 없으면 null 반환
     */
    Category findByName(String name);

    /**
     * 주어진 ID 리스트에 포함된 카테고리들을 검색합니다.
     *
     * @param ids 카테고리 ID 리스트
     * @return 검색된 카테고리 리스트
     */
    List<Category> findByIdIn(List<Integer> ids);
}
