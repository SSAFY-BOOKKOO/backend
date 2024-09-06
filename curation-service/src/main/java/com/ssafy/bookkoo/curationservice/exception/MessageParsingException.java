package com.ssafy.bookkoo.curationservice.exception;

public class MessageParsingException extends RuntimeException {

    public MessageParsingException() {
        super("메세지를 읽는데 실패했습니다.");
    }

}
