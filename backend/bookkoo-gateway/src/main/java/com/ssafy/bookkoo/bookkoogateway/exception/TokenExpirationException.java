package com.ssafy.bookkoo.bookkoogateway.exception;

public class TokenExpirationException extends RuntimeException {
    public TokenExpirationException() {
        super("액세스 토큰이 만료되었습니다. \n 다시 로그인해주세요.");
    }
}
