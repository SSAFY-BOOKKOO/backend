package com.ssafy.bookkoo.notificationservice.service;

import com.ssafy.bookkoo.notificationservice.dto.request.RequestCreateCommunityNotificationDto;
import com.ssafy.bookkoo.notificationservice.dto.request.RequestCreateCurationNotificationDto;
import com.ssafy.bookkoo.notificationservice.dto.request.RequestCreateFollowNotificationDto;
import com.ssafy.bookkoo.notificationservice.dto.response.ResponseNotificationDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NotificationService {

    void saveFollowNotification(RequestCreateFollowNotificationDto createFollowNotificationDto);

    void saveCurationNotification(RequestCreateCurationNotificationDto createCurationNotificationDto);

    void saveCommunityNotification(RequestCreateCommunityNotificationDto communityNotificationDto);

    void deleteNotification(Long memberId, Long notificationId);

    List<ResponseNotificationDto> getNotifications(Long memberId, Pageable pageable);

}
