package com.ssafy.bookkoo.memberservice.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bookkoo.memberservice.entity.FollowShip;
import com.ssafy.bookkoo.memberservice.entity.MemberInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.ssafy.bookkoo.memberservice.entity.QFollowShip.*;

@Repository
@RequiredArgsConstructor
public class FollowShipCustomRepositoryImpl implements FollowShipCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public FollowShip findByFollowerAndFollowee(MemberInfo follower, MemberInfo followee) {
        return queryFactory.selectFrom(followShip)
                           .where(followShip.follower.eq(follower), followShip.followee.eq(followee))
                           .fetchOne();
    }
}
