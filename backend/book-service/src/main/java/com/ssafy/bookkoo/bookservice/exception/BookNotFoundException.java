package com.ssafy.bookkoo.bookservice.exception;

/**
 * 책을 찾을 수 없는 경우 발생하는 예외 클래스입니다.
 */
public class BookNotFoundException extends RuntimeException {

    /**
     * 주어진 메시지로 예외를 생성합니다.
     *
     * @param message 예외 메시지
     */
    public BookNotFoundException(String message) {
        super(message);
    }

    /**
     * 기본 메시지로 예외를 생성합니다.
     */
    public BookNotFoundException() {
        super("책을 찾을 수 없습니다.");
    }
}
