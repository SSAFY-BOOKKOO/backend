package com.ssafy.bookkoo.bookservice.exception;

/**
 * 리뷰를 찾을 수 없는 경우 발생하는 예외 클래스입니다.
 */
public class ReviewNotFoundException extends RuntimeException {

    /**
     * 기본 메시지로 예외를 생성합니다.
     */
    public ReviewNotFoundException() {
        super("리뷰를 찾을 수 없습니다");
    }
}
