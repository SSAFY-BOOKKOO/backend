package com.ssafy.bookkoo.memberservice.repository.custom;

import com.ssafy.bookkoo.memberservice.entity.FollowShip;
import com.ssafy.bookkoo.memberservice.entity.MemberInfo;

public interface FollowShipCustomRepository {

    FollowShip findByFollowerAndFollowee(MemberInfo follower, MemberInfo followee);
}
