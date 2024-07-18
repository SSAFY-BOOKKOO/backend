package com.ssafy.bookkoo.memberservice.handler;

import com.ssafy.bookkoo.memberservice.exception.EmailNotValidException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MemberExceptionHandler {

    /**
     * 이메일 인증에 실패했을 때 발생하는 예외입니다.
     * @param e
     * @return
     */
    @ExceptionHandler(EmailNotValidException.class)
    public ResponseEntity<String> handleEmailDuplicateException(EmailNotValidException e) {
        return ResponseEntity.ok(e.getMessage());
    }
}
