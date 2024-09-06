package com.ssafy.bookkoo.bookservice.exception;

public class ReviewIsNotYoursException extends RuntimeException {

    public ReviewIsNotYoursException() {
        super("이 한줄평은 사용자의 한줄평이 아닙니다!");
    }
}
