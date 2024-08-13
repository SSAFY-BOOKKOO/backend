package com.ssafy.bookkoo.notificationservice.service;

import com.ssafy.bookkoo.notificationservice.client.MemberServiceClient;
import com.ssafy.bookkoo.notificationservice.client.dto.ResponseCurationReceiveDto;
import com.ssafy.bookkoo.notificationservice.client.dto.ResponseMemberInfoDto;
import com.ssafy.bookkoo.notificationservice.dto.request.RequestCreateCommunityNotificationDto;
import com.ssafy.bookkoo.notificationservice.dto.request.RequestCreateCurationNotificationDto;
import com.ssafy.bookkoo.notificationservice.dto.request.RequestCreateFollowNotificationDto;
import com.ssafy.bookkoo.notificationservice.dto.response.ResponseCommunityNotificationDto;
import com.ssafy.bookkoo.notificationservice.dto.response.ResponseCurationNotificationDto;
import com.ssafy.bookkoo.notificationservice.dto.response.ResponseFollowNotificationDto;
import com.ssafy.bookkoo.notificationservice.dto.response.ResponseNotificationDto;
import com.ssafy.bookkoo.notificationservice.entity.CommunityNotification;
import com.ssafy.bookkoo.notificationservice.entity.CurationNotification;
import com.ssafy.bookkoo.notificationservice.entity.FollowNotification;
import com.ssafy.bookkoo.notificationservice.entity.Notification;
import com.ssafy.bookkoo.notificationservice.exception.NotificationNotFoundException;
import com.ssafy.bookkoo.notificationservice.exception.UnAuthorizationException;
import com.ssafy.bookkoo.notificationservice.mapper.NotificationMapper;
import com.ssafy.bookkoo.notificationservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final MemberServiceClient memberServiceClient;

    /**
     * 팔로워 알림 생성
     *
     * @param createFollowNotificationDto
     */
    @Override
    @Transactional
    public void saveFollowNotification(
        RequestCreateFollowNotificationDto createFollowNotificationDto) {
        FollowNotification followNotification = notificationMapper.toFollowNotification(
            createFollowNotificationDto);
        notificationRepository.save(followNotification);
    }

    /**
     * 큐레이션 수신 알림 생성
     *
     * @param createCurationNotificationDto
     */
    @Override
    @Transactional
    public void saveCurationNotification(
        RequestCreateCurationNotificationDto createCurationNotificationDto) {
        for (Long memberId : createCurationNotificationDto.memberIds()) {
            CurationNotification curationNotification = notificationMapper.toCurationNotification(
                createCurationNotificationDto);
            curationNotification.setMemberId(memberId);
            notificationRepository.save(curationNotification);
        }
    }

    /**
     * 커뮤니티 삭제 예정 알림 생성
     *
     * @param communityNotificationDto
     */
    @Override
    @Transactional
    public void saveCommunityNotification(
        RequestCreateCommunityNotificationDto communityNotificationDto) {
        for (Long memberId : communityNotificationDto.memberIds()) {
            CommunityNotification communityNotification = notificationMapper.toCommunityNotification(
                communityNotificationDto);
            communityNotification.setMemberId(memberId);
            notificationRepository.save(communityNotification);
        }
    }

    /**
     * 알림 삭제
     * @param memberId
     * @param notificationId
     */
    @Override
    @Transactional
    public void deleteNotification(Long memberId, Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                                                          .orElseThrow(NotificationNotFoundException::new);
        Long notificationMemberId = notification.getMemberId();
        if (!notificationMemberId.equals(memberId)) {
            throw new UnAuthorizationException();
        }
        notificationRepository.delete(notification);
    }

    /**
     * 멤버에게 온 알림을 페이징해서 반환
     * 타입별 추가 정보 필드 채워서 반환
     * @param id
     * @param pageable
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<ResponseNotificationDto> getNotifications(Long id, Pageable pageable) {
        List<Notification> notifications = notificationRepository.findByMemberIdAndCondition(id, pageable);
        return notifications.stream()
                            .map(notification -> {
                                if (notification instanceof CommunityNotification communityNotification) {
                                    return ResponseCommunityNotificationDto.toDto(communityNotification);
                                } else if (notification instanceof CurationNotification curationNotification) {
                                    ResponseMemberInfoDto memberInfo
                                        = memberServiceClient.getMemberInfo(curationNotification.getWriterId());
                                    return ResponseCurationNotificationDto.toDto(
                                        curationNotification, memberInfo);
                                } else {
                                    FollowNotification followNotification = (FollowNotification) notification;
                                    ResponseMemberInfoDto memberInfo
                                        = memberServiceClient.getMemberInfo(followNotification.getFollowerId());
                                    return ResponseFollowNotificationDto.toDto(
                                        followNotification, memberInfo);
                                }
                            }).toList();
    }
}
