package com.ssafy.bookkoo.notificationservice.mapper;

import com.ssafy.bookkoo.notificationservice.dto.request.RequestCreateCommunityNotificationDto;
import com.ssafy.bookkoo.notificationservice.dto.request.RequestCreateCurationNotificationDto;
import com.ssafy.bookkoo.notificationservice.dto.request.RequestCreateFollowNotificationDto;
import com.ssafy.bookkoo.notificationservice.dto.response.ResponseCommunityNotificationDto;
import com.ssafy.bookkoo.notificationservice.dto.response.ResponseCurationNotificationDto;
import com.ssafy.bookkoo.notificationservice.dto.response.ResponseFollowNotificationDto;
import com.ssafy.bookkoo.notificationservice.entity.CommunityNotification;
import com.ssafy.bookkoo.notificationservice.entity.CurationNotification;
import com.ssafy.bookkoo.notificationservice.entity.FollowNotification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mapping(target = "id", ignore = true)
    FollowNotification toFollowNotification(RequestCreateFollowNotificationDto followNotificationDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "memberId", source = "memberIds", ignore = true)
    CurationNotification toCurationNotification(RequestCreateCurationNotificationDto curationNotificationDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "memberId", source = "memberIds", ignore = true)
    CommunityNotification toCommunityNotification(RequestCreateCommunityNotificationDto communityNotificationDto);
}
