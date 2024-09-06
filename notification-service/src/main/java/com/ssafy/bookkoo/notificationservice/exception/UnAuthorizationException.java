package com.ssafy.bookkoo.notificationservice.exception;

/**
 * 해당 알림에 대한 접근 권한이 없는 경우 발생하는 예외 (남에 알리 삭제 등)
 */
public class UnAuthorizationException extends RuntimeException {
    public UnAuthorizationException() {
        super("해당 알림에 대한 권한이 없습니다.");
    }
}
