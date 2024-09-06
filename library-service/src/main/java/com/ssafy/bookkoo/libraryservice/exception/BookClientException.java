package com.ssafy.bookkoo.libraryservice.exception;

public class BookClientException extends RuntimeException {

    public BookClientException(String message) {
        super("책 서비스와 통신 중 에러가 발생했습니다 :  " + message);
    }
}
