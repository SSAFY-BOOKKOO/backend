package com.ssafy.bookkoo.memberservice.repository;

import com.ssafy.bookkoo.memberservice.entity.MemberInfo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberInfoRepository extends JpaRepository<MemberInfo, Long> {

    Optional<MemberInfo> findByNickName(String nickName);

    Optional<MemberInfo> findByMemberId(String memberId);
}
