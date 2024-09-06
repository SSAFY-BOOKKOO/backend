package com.ssafy.bookkoo.bookservice.exception;

/**
 * 카테고리를 찾을 수 없는 경우 발생하는 예외 클래스입니다.
 */
public class CategoryNotFoundException extends RuntimeException {

    /**
     * 주어진 메시지로 예외를 생성합니다.
     *
     * @param message 예외 메시지
     */
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
