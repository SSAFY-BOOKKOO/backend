package com.ssafy.bookkoo.libraryservice.exception;

/**
 * 해당 멤버가 존재하지 않을 때 발생하는 예외
 */
public class MemberNotFoundException extends RuntimeException {

    /**
     * 기본 생성자. "멤버가 존재하지 않습니다." 메시지를 포함합니다.
     */
    public MemberNotFoundException() {
        super("멤버가 존재하지 않습니다.");
    }

    /**
     * 주어진 이메일에 대한 예외 메시지를 포함하는 생성자입니다.
     *
     * @param email 멤버 이메일
     */
    public MemberNotFoundException(String email) {
        super(email + "에 해당하는 멤버가 존재하지 않습니다.");
    }
}
