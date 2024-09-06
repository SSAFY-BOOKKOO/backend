package com.ssafy.bookkoo.memberservice.exception;

/**
 * 글귀가 존재하지 않을 때 발생하는 예외
 */
public class QuoteNotFoundException extends RuntimeException {

    public QuoteNotFoundException() {
        super("글귀가 존재하지 않습니다.");
    }
}
