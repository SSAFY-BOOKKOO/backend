package com.ssafy.bookkoo.memberservice.exception;

/**
 * 닉네임 중복 검사 시 중복되는 닉네임일 때 발생하는 예외
 */
public class NickNameDuplicateException extends RuntimeException {

    public NickNameDuplicateException() {
        super("중복되는 닉네임입니다.");
    }
}
