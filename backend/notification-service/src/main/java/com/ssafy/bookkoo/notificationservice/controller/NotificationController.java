package com.ssafy.bookkoo.notificationservice.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @GetMapping
    public ResponseEntity<HttpEntity> test() {
        return ResponseEntity.ok()
                             .build();
    }
}
