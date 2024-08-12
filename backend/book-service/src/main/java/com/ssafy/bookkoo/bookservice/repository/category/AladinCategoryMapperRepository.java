package com.ssafy.bookkoo.bookservice.repository.category;

import com.ssafy.bookkoo.bookservice.entity.AladinCategoryMapper;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * AladinCategoryMapper 엔티티에 대한 JPA 레포지토리 인터페이스입니다.
 */
public interface AladinCategoryMapperRepository extends
    JpaRepository<AladinCategoryMapper, Integer> {

    /**
     * 주어진 알라딘 카테고리 ID로 AladinCategoryMapper를 검색합니다.
     *
     * @param aladinCategoryId 알라딘 카테고리 ID
     * @return 검색된 AladinCategoryMapper 객체
     */
    AladinCategoryMapper findByAladinCategoryId(Integer aladinCategoryId);
}
