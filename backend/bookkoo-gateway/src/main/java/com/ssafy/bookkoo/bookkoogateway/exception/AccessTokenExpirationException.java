package com.ssafy.bookkoo.bookkoogateway.exception;

/**
 * 액세스 토큰 만료 시 발생하는 예외
 */
public class AccessTokenExpirationException extends RuntimeException {
    public AccessTokenExpirationException() {
        super("액세스 토큰이 만료되었습니다.");
    }
}
