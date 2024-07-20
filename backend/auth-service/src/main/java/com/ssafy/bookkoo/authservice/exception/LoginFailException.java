package com.ssafy.bookkoo.authservice.exception;

/**
 * 로그인 실패 시 발생하는 예외
 */
public class LoginFailException extends RuntimeException {

    public LoginFailException() {
        super("로그인에 실패하였습니다.");
    }
}
