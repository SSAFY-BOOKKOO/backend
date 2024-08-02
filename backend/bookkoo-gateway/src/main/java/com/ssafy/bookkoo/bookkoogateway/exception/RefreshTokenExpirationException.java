package com.ssafy.bookkoo.bookkoogateway.exception;

/**
 * 리프레시 토큰 만료 시 발생하는 예외
 */
public class RefreshTokenExpirationException extends RuntimeException {
    public RefreshTokenExpirationException() {
        super("리프레시 토큰이 만료되었습니다.");
    }
}
