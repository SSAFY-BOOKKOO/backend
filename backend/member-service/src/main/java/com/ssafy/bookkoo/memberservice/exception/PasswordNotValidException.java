package com.ssafy.bookkoo.memberservice.exception;

/**
 * 비밀번호가 유효하지 않을 떄 발생하는 예외 (영어, 숫자, 특수기호 8~16자)
 */
public class PasswordNotValidException extends RuntimeException {

    public PasswordNotValidException() {
        super("비밀번호 형식이 유효하지 않습니다.");
    }
}
