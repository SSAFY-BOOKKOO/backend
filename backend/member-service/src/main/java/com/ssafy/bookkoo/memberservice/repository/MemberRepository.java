package com.ssafy.bookkoo.memberservice.repository;

import com.ssafy.bookkoo.memberservice.entity.Member;
import com.ssafy.bookkoo.memberservice.repository.custom.MemberCustomRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>,
    MemberCustomRepository {

    /**
     * 이메일을 통해 멤버 찾기
     *
     * @param email
     * @return
     */
    Optional<Member> findByEmail(String email);
}
