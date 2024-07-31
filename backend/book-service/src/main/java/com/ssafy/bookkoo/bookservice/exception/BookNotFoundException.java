package com.ssafy.bookkoo.bookservice.exception;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(String message) {
        super(message);
    }

    public BookNotFoundException() {
        super("책을 찾을 수 없습니다.");
    }
}
