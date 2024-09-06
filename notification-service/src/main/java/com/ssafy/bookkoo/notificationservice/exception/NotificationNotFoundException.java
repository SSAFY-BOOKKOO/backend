package com.ssafy.bookkoo.notificationservice.exception;

/**
 * 알림이 존재하지 않을 때 발생하는 예외
 */
public class NotificationNotFoundException extends RuntimeException {
    public NotificationNotFoundException() {
        super("알림이 존재하지 않습니다.");
    }
}
