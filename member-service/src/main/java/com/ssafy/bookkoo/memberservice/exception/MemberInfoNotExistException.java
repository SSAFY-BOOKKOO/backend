package com.ssafy.bookkoo.memberservice.exception;

/**
 * 멤버 정보를 아직 입력하지 않았을 때(존재하지 않을 떄) 발생하는 예외 입니다.
 */
public class MemberInfoNotExistException extends RuntimeException {

    public MemberInfoNotExistException() {
        super("멤버가 추가 정보를 아직 입력하지 않았습니다.");
    }
}
