package com.ssafy.bookkoo.authservice.repository;

import com.ssafy.bookkoo.authservice.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    Optional<Member> findByMemberId(String memberId);
}
