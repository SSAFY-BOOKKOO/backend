package com.ssafy.bookkoo.booktalkservice.exception;

public class ChatMessageNotFoundException extends RuntimeException {

    public ChatMessageNotFoundException() {
        super("해당 채팅을 찾지 못했습니다.");
    }

}
