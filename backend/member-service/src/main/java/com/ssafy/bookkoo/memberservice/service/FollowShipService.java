package com.ssafy.bookkoo.memberservice.service;

import com.ssafy.bookkoo.memberservice.dto.response.ResponseFollowShipDto;

import java.util.List;

public interface FollowShipService {

    void follow(Long followerId, Long followeeId);

    void unFollow(Long followerId, Long followeeId);

    List<Long> getFollowerIds(Long memberId);

    List<Long> getFolloweeIds(Long memberId);

    List<ResponseFollowShipDto> getFollowers(Long memberId);

    List<ResponseFollowShipDto> getFollowees(Long memberId);
}
