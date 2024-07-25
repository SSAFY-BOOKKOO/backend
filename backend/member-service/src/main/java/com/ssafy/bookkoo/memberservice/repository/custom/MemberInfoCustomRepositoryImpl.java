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
