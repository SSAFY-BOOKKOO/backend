package com.ssafy.bookkoo.memberservice.exception;

/**
 * 이메일 중복 검사 시 중복되는 이메일일 때 발생하는 예외
 */
public class EmailDuplicateException extends RuntimeException {

    public EmailDuplicateException() {
        super("중복되는 이메일 입니다.");
    }
}
