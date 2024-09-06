package com.ssafy.bookkoo.memberservice.exception;

/**
 * 이메일이 중복될 때 발생하는 예외
 */
public class EmailNotValidException extends RuntimeException {
    public EmailNotValidException() {
        super("이메일 인증번호가 일치하지 않습니다.");
    }
}
