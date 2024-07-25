package com.ssafy.bookkoo.memberservice.service;

import com.ssafy.bookkoo.memberservice.dto.response.ResponseFollowShipDto;

import java.util.List;

public interface FollowShipService {

    void follow(Long followerId, Long followeeId);

    void unFollow(Long followerId, Long followeeId);

    List<ResponseFollowShipDto> getFollowers(Long memberId);

    List<ResponseFollowShipDto> getFollowees(Long memberId);
}
