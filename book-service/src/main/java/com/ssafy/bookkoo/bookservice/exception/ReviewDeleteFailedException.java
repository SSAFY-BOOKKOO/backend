package com.ssafy.bookkoo.bookservice.exception;

public class ReviewDeleteFailedException extends RuntimeException {

    public ReviewDeleteFailedException() {
        super("리뷰 삭제에 실패하였습니다.");
    }
}
