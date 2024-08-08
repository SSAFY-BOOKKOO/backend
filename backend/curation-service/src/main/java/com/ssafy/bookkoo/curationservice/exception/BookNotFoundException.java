package com.ssafy.bookkoo.curationservice.exception;

/**
 * 책을 찾을 수 없는 경우 발생하는 예외 클래스입니다.
 */
public class BookNotFoundException extends RuntimeException {

    /**
     * 주어진 메시지로 예외를 생성합니다.
     *
     * @param bookid 책 번호
     */
    public BookNotFoundException(Long bookid) {
        super("해당 번호를 가진 책을 찾지 못했습니다. bookId = " + bookid);
    }

    /**
     * 기본 메시지로 예외를 생성합니다.
     */
    public BookNotFoundException() {
        super("책을 찾지 못했습니다.");
    }
}
