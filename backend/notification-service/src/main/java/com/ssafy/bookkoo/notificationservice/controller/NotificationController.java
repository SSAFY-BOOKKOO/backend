package com.ssafy.bookkoo.notificationservice.controller;

import com.ssafy.bookkoo.notificationservice.dto.request.RequestCreateCommunityNotificationDto;
import com.ssafy.bookkoo.notificationservice.dto.request.RequestCreateCurationNotificationDto;
import com.ssafy.bookkoo.notificationservice.dto.request.RequestCreateFollowNotificationDto;
import com.ssafy.bookkoo.notificationservice.dto.response.ResponseNotificationDto;
import com.ssafy.bookkoo.notificationservice.service.NotificationService;
import com.ssafy.bookkoo.notificationservice.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/follow")
    @Operation(summary = "팔로우 관련 알림 생성 API", description = "팔로우 관련 알림을 생성합니다.")
    public ResponseEntity<HttpStatus> createFollowNotification(
        @Valid @RequestBody RequestCreateFollowNotificationDto requestCreateFollowNotificationDto
    ) {
        log.info("{}", requestCreateFollowNotificationDto);
        notificationService.saveFollowNotification(requestCreateFollowNotificationDto);
        return ResponseEntity.ok()
                             .build();
    }

    @PostMapping("/curation")
    @Operation(summary = "큐레이션 관련 알림 생성 API", description = "큐레이션 관련 알림을 생성합니다.")
    public ResponseEntity<HttpStatus> createCurationNotification(
        @Valid @RequestBody RequestCreateCurationNotificationDto createCurationNotificationDto
    ) {
        notificationService.saveCurationNotification(createCurationNotificationDto);
        return ResponseEntity.ok()
                             .build();
    }

    @PostMapping("/community")
    @Operation(summary = "커뮤니티 관련 알림 생성 API", description = "커뮤니티 관련 알림을 생성합니다.")
    public ResponseEntity<HttpStatus> createCommunityNotification(
        @Valid @RequestBody RequestCreateCommunityNotificationDto createCommunityNotificationDto
    ) {
        notificationService.saveCommunityNotification(createCommunityNotificationDto);
        return ResponseEntity.ok()
                             .build();
    }

    @DeleteMapping("/{notificationId}")
    @Operation(summary = "알림 삭제 API", description = "멤버 자신에게 온 알림을 삭제합니다.")
    public ResponseEntity<HttpStatus> deleteNotification(
        @RequestHeader HttpHeaders headers,
        @PathVariable(name = "notificationId") Long notificationId
    ) {
        Long memberId = CommonUtil.getMemberId(headers);
        notificationService.deleteNotification(memberId, notificationId);
        return ResponseEntity.noContent()
                             .build();
    }

    @GetMapping
    @Operation(summary = "알림 목록 반환 API",
        description = "멤버 자신에게 온 알림 목록을 반환합니다. 페이징 Default(page = 0, size = 10)")
    public ResponseEntity<List<ResponseNotificationDto>> getNotifications(
        @RequestHeader HttpHeaders headers,
        @PageableDefault
        Pageable pageable
    ) {
        Long memberId = CommonUtil.getMemberId(headers);
        List<ResponseNotificationDto> notifications = notificationService.getNotifications(memberId, pageable);
        return ResponseEntity.ok(notifications);
    }
}
