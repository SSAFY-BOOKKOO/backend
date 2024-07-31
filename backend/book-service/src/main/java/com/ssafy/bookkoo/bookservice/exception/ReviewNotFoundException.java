package com.ssafy.bookkoo.bookservice.exception;

public class ReviewNotFoundException extends RuntimeException {

    public ReviewNotFoundException() {
        super("리뷰를 찾을 수 없습니다");
    }
}
