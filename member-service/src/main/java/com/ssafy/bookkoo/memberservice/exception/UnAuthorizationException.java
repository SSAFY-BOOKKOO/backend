package com.ssafy.bookkoo.memberservice.exception;

/**
 * 해당 글귀에 대한 접근 권한이 없는 경우 발생하는 예외
 */
public class UnAuthorizationException extends RuntimeException {
    public UnAuthorizationException() {
        super("해당 글귀에 대한 권한이 없습니다.");
    }
}
