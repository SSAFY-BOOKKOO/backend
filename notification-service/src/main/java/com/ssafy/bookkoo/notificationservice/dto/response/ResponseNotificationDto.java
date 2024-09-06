package com.ssafy.bookkoo.notificationservice.dto.response;

import com.ssafy.bookkoo.notificationservice.enums.NotificationType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class ResponseNotificationDto {

    //알림 ID
    private Long notificationId;

    //알림 타입
    private NotificationType notificationType;

    //알림 생성 시간
    private LocalDateTime createdAt;
}
