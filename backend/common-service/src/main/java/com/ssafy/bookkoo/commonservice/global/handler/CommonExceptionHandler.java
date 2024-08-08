package com.ssafy.bookkoo.commonservice.global.handler;

import com.ssafy.bookkoo.commonservice.email.exception.EmailSendFailException;
import com.ssafy.bookkoo.commonservice.s3.exception.FileDeleteFailException;
import com.ssafy.bookkoo.commonservice.s3.exception.FileSaveFailException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {

    /**
     * 파일 저장에 실패했을 때 발생하는 예외
     * @param e
     * @return
     */
    @ExceptionHandler(FileSaveFailException.class)
    public ResponseEntity<String> handleFileSaveFailException(FileSaveFailException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(e.getMessage());
    }

    /**
     * 파일 삭제에 실패했을 때 발생하는 예외
     * @param e
     * @return
     */
    @ExceptionHandler(FileDeleteFailException.class)
    public ResponseEntity<String> handleFileDeleteFailException(FileDeleteFailException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(e.getMessage());
    }

    /**
     * 이메일 전송에 실패했을 때 발생하는 예외
     * @param e
     * @return
     */
    @ExceptionHandler(EmailSendFailException.class)
    public ResponseEntity<String> handleEmailSendFailException(EmailSendFailException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(e.getMessage());
    }
}