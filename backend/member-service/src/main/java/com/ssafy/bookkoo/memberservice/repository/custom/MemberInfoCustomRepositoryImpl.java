package com.ssafy.bookkoo.memberservice.repository.custom;

import static com.ssafy.bookkoo.memberservice.entity.QMemberInfo.memberInfo;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberInfoCustomRepositoryImpl implements MemberInfoCustomRepository{

    private final JPAQueryFactory queryFactory;

    /**
     * follwers에 속한 Member를 제외하고
     * 랜덤으로 3명의 Member ID(Long)을 반환합니다.
     * @param followers
     * @return
     */
    @Override
    public List<Long> findRandomMemberInfoIdByFollowers(List<Long> followers) {
        return queryFactory.select(memberInfo.id)
                           .from(memberInfo)
                           .where(memberInfo.id.notIn(followers))
                           .orderBy(Expressions.numberTemplate(Double.class,
                                                               "function('RANDOM')")
                                                           .asc())
                           .limit(3)
                           .fetch();
    }
}
