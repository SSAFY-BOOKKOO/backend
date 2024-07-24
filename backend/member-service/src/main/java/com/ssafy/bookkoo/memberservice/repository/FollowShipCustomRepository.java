package com.ssafy.bookkoo.memberservice.repository;

import com.ssafy.bookkoo.memberservice.entity.FollowShip;
import com.ssafy.bookkoo.memberservice.entity.MemberInfo;

import java.util.Optional;

public interface FollowShipCustomRepository {

    FollowShip findByFollowerAndFollowee(MemberInfo follower, MemberInfo followee);
}
