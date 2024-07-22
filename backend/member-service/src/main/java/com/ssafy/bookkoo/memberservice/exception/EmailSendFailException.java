package com.ssafy.bookkoo.memberservice.exception;

/**
 * 이메일 전송 실패 시 발생하는 예외
 */
public class EmailSendFailException extends RuntimeException{

    public EmailSendFailException() {
        super("이메일 발송에 실패했습니다.");
    }
}
