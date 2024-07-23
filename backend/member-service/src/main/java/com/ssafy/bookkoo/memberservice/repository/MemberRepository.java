package com.ssafy.bookkoo.memberservice.repository;

import com.ssafy.bookkoo.memberservice.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    /**
     * 이메일을 통해 멤버 찾기
     *
     * @param email
     * @return
     */
    Optional<Member> findByEmail(String email);

    /**
     * 멤버 고유 ID (UUID)를 통해 멤버 찾기
     *
     * @param memberId
     * @return
     */
    Optional<Member> findByMemberId(String memberId);
}
