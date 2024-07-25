package com.ssafy.bookkoo.memberservice.exception;

public class FollowShipNotFoundException extends RuntimeException {

    public FollowShipNotFoundException() {
        super("팔로우 관계를 찾을 수 없습니다.");
    }
}
