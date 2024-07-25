package com.ssafy.bookkoo.memberservice.repository;

import com.ssafy.bookkoo.memberservice.entity.MemberCategoryMapper;
import com.ssafy.bookkoo.memberservice.entity.MemberCategoryMapperKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCategoryMapperRepository extends
    JpaRepository<MemberCategoryMapper, MemberCategoryMapperKey> {

}
