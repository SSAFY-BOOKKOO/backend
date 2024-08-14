package com.ssafy.bookkoo.memberservice.exception;

/**
 * 알림 생성에 실패하였을 때 (팔로우) 발생하는 예외
 */
public class CreateNotificationFailException extends RuntimeException {
    
    public CreateNotificationFailException() {
        super("알림 생성에 실패하였습니다.");
    }
}
