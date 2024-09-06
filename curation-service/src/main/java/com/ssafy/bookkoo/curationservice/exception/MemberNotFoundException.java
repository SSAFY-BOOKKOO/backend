package com.ssafy.bookkoo.curationservice.exception;

public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException() {
        super("회원 정보를 찾지 못했습니다.");
    }

}
