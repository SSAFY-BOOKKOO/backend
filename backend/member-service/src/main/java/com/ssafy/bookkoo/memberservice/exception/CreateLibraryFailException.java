package com.ssafy.bookkoo.memberservice.exception;

/**
 * 회원가입 시 기본 서재 생성 로직에서 실패 시 발생하는 예외입니다.
 */
public class CreateLibraryFailException extends RuntimeException {

    public CreateLibraryFailException() {
        super("기본 서재 생성에 실패했습니다.");
    }
}
