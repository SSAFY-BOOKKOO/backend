package com.ssafy.bookkoo.memberservice.client;

import com.ssafy.bookkoo.memberservice.dto.request.RequestCreateFollowNotificationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service")
public interface NotificationServiceClient {

    @PostMapping("/notifications/follow")
    ResponseEntity<HttpStatus> createFollowNotification(
        @RequestBody RequestCreateFollowNotificationDto createFollowNotificationDto);


}
