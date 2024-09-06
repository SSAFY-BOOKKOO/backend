package com.ssafy.bookkoo.curationservice.client;

import com.ssafy.bookkoo.curationservice.dto.RequestCreateCurationNotificationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service")
public interface NotificationServiceClient {

    @PostMapping("/notifications/curation")
    public ResponseEntity<HttpStatus> createCurationNotification(
        @RequestBody RequestCreateCurationNotificationDto createCurationNotificationDto
    );

}
