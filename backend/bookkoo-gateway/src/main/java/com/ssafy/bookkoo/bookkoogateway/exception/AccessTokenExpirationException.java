package com.ssafy.bookkoo.bookkoogateway.exception;

public class AccessTokenExpirationException extends RuntimeException {
    public AccessTokenExpirationException() {
        super("액세스 토큰이 만료되었습니다.");
    }
}
