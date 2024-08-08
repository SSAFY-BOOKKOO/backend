package com.ssafy.bookkoo.booktalkservice.exception;

/**
 * 한 책당 하나의 독서록만 가능하기 때문에 같은 책에 대해 독서록 생성 요청을 할 경우 발생하는 예외
 */
public class BookTalkAlreadyExistException extends RuntimeException {

    public BookTalkAlreadyExistException() {
        super("이미 해당 책의 독서록이 존재합니다.");
    }
}
