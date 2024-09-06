package com.ssafy.bookkoo.memberservice.repository.custom;

import static com.ssafy.bookkoo.memberservice.entity.QMember.member;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository{

    private final JPAQueryFactory queryFactory;

    /**
     * 멤버 ID를 통해 이메일을 가져오는 로직
     * @param id
     * @return
     */
    @Override
    public String findEmailById(Long id) {
        return queryFactory.select(member.email)
                           .from(member)
                           .where(member.id.eq(id))
                           .fetchOne();
    }
}
