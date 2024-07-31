package com.ssafy.bookkoo.bookservice.repository.category;

import com.ssafy.bookkoo.bookservice.entity.AladinCategoryMapper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AladinCategoryMapperRepository extends
    JpaRepository<AladinCategoryMapper, Integer> {

    AladinCategoryMapper findByAladinCategoryId(Integer aladinCategoryId);
}
