package com.ssafy.bookkoo.bookservice.exception;

public class AladdinAPIException extends RuntimeException {

    public AladdinAPIException() {
        super("알라딘 API에서 책 정보를 가져오는데 실패했습니다.");
    }
}
