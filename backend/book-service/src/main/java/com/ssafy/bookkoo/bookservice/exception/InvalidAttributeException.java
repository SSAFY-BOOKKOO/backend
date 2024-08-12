package com.ssafy.bookkoo.bookservice.exception;

/**
 * 잘못된 속성이 있는 경우 발생하는 예외 클래스입니다.
 */
public class InvalidAttributeException extends RuntimeException {

    /**
     * 주어진 메시지로 예외를 생성합니다.
     *
     * @param message 예외 메시지
     */
    public InvalidAttributeException(String message) {
        super(message);
    }
}
