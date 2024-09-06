package com.ssafy.bookkoo.curationservice.exception;

public class OpenAiException extends RuntimeException {

    public OpenAiException() {
        super("챗봇이 응답 생성을 실패했어요");
    }

}
