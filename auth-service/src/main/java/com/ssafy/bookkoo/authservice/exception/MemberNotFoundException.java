package com.ssafy.bookkoo.authservice.exception;

/**
 * 해당 멤버가 존재하지 않을 때 발생하는 예외
 */
public class MemberNotFoundException extends RuntimeException{

    public MemberNotFoundException() {
        super("멤버가 존재하지 않습니다.");
    }

    public MemberNotFoundException(String email) {
        super(email + "에 해당하는 멤버가 존재하지 않습니다.");
    }
}
