package com.ssafy.bookkoo.libraryservice.exception;

/**
 * 책이 이미 서재에 매핑되어 있을 때 발생하는 예외 클래스입니다.
 */
public class BookAlreadyMappedException extends RuntimeException {

    /**
     * 기본 생성자. "책이 이미 서재에 들어가있습니다!" 메시지를 포함합니다.
     */
    public BookAlreadyMappedException() {
        super("책이 이미 서재에 들어가있습니다!");
    }
}
