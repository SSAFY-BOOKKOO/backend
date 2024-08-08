package com.ssafy.bookkoo.commonservice.email.exception;

public class EmailSendFailException extends RuntimeException {

    public EmailSendFailException() {
        super("이메일 발송에 실패했습니다.");
    }
}
