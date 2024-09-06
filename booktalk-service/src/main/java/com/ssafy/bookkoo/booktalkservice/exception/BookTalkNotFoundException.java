package com.ssafy.bookkoo.booktalkservice.exception;


/**
 * 없는 독서록 정보를 찾을때 발생하는 예외
 */
public class BookTalkNotFoundException extends RuntimeException {

    public BookTalkNotFoundException() {
        super("독서록을 찾을 수 없습니다.");
    }

    public BookTalkNotFoundException(Long bookTalkId) {
        super("독서록을 찾을 수 없습니다. bookTalkId = " + bookTalkId);
    }

}
