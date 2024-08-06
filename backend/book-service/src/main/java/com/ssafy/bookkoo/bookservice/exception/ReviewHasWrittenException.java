package com.ssafy.bookkoo.bookservice.exception;

public class ReviewHasWrittenException extends RuntimeException {

    public ReviewHasWrittenException() {
        super("해당 책에 대해 이미 작성된 리뷰가 존재합니다.");
    }
}
