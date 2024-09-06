package com.ssafy.bookkoo.memberservice.exception;

/**
 * 이미 팔로우한 경우 발생하는 예외
 */
public class AlreadyFollowedException extends RuntimeException {

    public AlreadyFollowedException() {
        super("이미 팔로우한 상태입니다.");
    }
}
