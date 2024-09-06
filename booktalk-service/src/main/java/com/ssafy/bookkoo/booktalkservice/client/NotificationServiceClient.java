package com.ssafy.bookkoo.booktalkservice.client;

import com.ssafy.bookkoo.booktalkservice.dto.RequestCreateCommunityNotificationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service")
public interface NotificationServiceClient {

    @PostMapping("/notifications/community")
    ResponseEntity<HttpStatus> createCommunityNotification(
        @RequestBody RequestCreateCommunityNotificationDto createCurationNotificationDto
    );

}
