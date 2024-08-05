package com.ssafy.bookkoo.memberservice.service.Impl;

import com.ssafy.bookkoo.memberservice.client.NotificationServiceClient;
import com.ssafy.bookkoo.memberservice.dto.request.RequestCreateFollowNotificationDto;
import com.ssafy.bookkoo.memberservice.dto.response.ResponseFollowShipDto;
import com.ssafy.bookkoo.memberservice.entity.FollowShip;
import com.ssafy.bookkoo.memberservice.entity.MemberInfo;
import com.ssafy.bookkoo.memberservice.exception.FollowShipNotFoundException;
import com.ssafy.bookkoo.memberservice.exception.MemberNotFoundException;
import com.ssafy.bookkoo.memberservice.repository.FollowShipRepository;
import com.ssafy.bookkoo.memberservice.repository.MemberInfoRepository;
import com.ssafy.bookkoo.memberservice.service.FollowShipService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowShipServiceImpl implements FollowShipService {

    private final FollowShipRepository followShipRepository;
    private final MemberInfoRepository memberInfoRepository;
    private final NotificationServiceClient notificationServiceClient;

    /**
     * 팔로잉 관계를 추가합니다.
     *
     * @param followerId : 팔로우를 요청한 멤버
     * @param followeeId : 팔로잉 되는 멤버
     */
    @Override
    @Transactional
    public void follow(Long followerId, Long followeeId) {
        MemberInfo followerInfo = getMemberInfo(followerId);
        MemberInfo followeeInfo = getMemberInfo(followeeId);

        FollowShip followShip = FollowShip.builder()
                                          .follower(followerInfo)
                                          .followee(followeeInfo)
                                          .build();

        followShipRepository.save(followShip);
        //팔로우 요청
        //1.요청자는 팔로워 대상의 팔로워에 들어간다.
        followerInfo.addFollowees(followShip, followeeInfo);
        //2.팔로워 대상은 요청자를 팔로워에 추가한다.
        followeeInfo.addFollowers(followShip, followerInfo);

        //팔로우 대상에게 알림을 전송
        RequestCreateFollowNotificationDto createFollowNotificationDto
            = RequestCreateFollowNotificationDto.builder()
                                                .followerId(followerInfo.getMemberId())
                                                .memberId(followeeInfo.getMemberId())
                                                .build();
        notificationServiceClient.createFollowNotification(createFollowNotificationDto);
    }


    /**
     * 팔로우 관계를 삭제합니다.
     * @param followeeId
     * @param followerId
     */
    @Override
    @Transactional
    public void unFollow(Long followerId, Long followeeId) {
        MemberInfo followeeInfo = getMemberInfo(followeeId);
        MemberInfo followerInfo = getMemberInfo(followerId);
        FollowShip followShip = followShipRepository.findByFollowerAndFollowee(followerInfo, followeeInfo);
        if (followShip == null) {
            throw new FollowShipNotFoundException();
        }
        //언팔로우 요청
        //1. 언팔로우 요청자의 팔로잉 목록에서 삭제
        followeeInfo.removeFollowee(followShip);
        //2. 언팔로우 대상자의 팔로워 목록에서 삭제
        followerInfo.removeFollower(followShip);
        followShipRepository.delete(followShip);
    }

    /**
     * 멤버를 팔로우하는 멤버들의 ID를 반환합니다.
     * @param memberId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<ResponseFollowShipDto> getFollowers(Long memberId) {
        MemberInfo memberInfo = getMemberInfo(memberId);

        return memberInfo.getFollowers()
                         .stream()
                         .map((followShip) -> ResponseFollowShipDto.builder()
                                                                   .memberId(
                                                                       followShip.getFollower()
                                                                                 .getId())
                                                                   .build())
                         .toList();
    }

    /**
     * 멤버가 팔로우 하는 멤버들의 ID를 반환합니다.
     * @param memberId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<ResponseFollowShipDto> getFollowees(Long memberId) {
        MemberInfo memberInfo = getMemberInfo(memberId);

        return memberInfo.getFollowees()
                         .stream()
                         .map((followShip) -> ResponseFollowShipDto.builder()
                                                                   .memberId(followShip.getFollowee()
                                                                                       .getId())
                                                                   .build())
                         .collect(Collectors.toList());
    }

    /**
     * memberId(Long)을 통해 멤버 정보를 반환합니다.
     *
     * @param memberId
     * @return
     */
    private MemberInfo getMemberInfo(Long memberId) {
        return memberInfoRepository.findById(memberId)
                                   .orElseThrow(MemberNotFoundException::new);
    }
}
