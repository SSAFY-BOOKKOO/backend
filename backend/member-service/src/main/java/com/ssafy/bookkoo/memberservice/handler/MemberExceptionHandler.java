package com.ssafy.bookkoo.memberservice.handler;

import com.ssafy.bookkoo.memberservice.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MemberExceptionHandler {

    /**
     * 이메일 인증에 실패했을 때 발생하는 예외입니다.
     *
     * @param e
     * @return
     */
    @ExceptionHandler(EmailNotValidException.class)
    public ResponseEntity<String> handleEmailDuplicateException(EmailNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(e.getMessage());
    }

    /**
     * 이메일이 중복될 때 발생하는 예외입니다.
     *
     * @param e
     * @return
     */
    @ExceptionHandler(EmailDuplicateException.class)
    public ResponseEntity<String> handleEmailDuplicateException(EmailDuplicateException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                             .body(e.getMessage());
    }

    /**
     * 이메일 전송에 실패했을 때 발생하는 예외입니다.
     *
     * @param e
     * @return
     */
    @ExceptionHandler(EmailSendFailException.class)
    public ResponseEntity<String> handleEmailSendFailException(EmailSendFailException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(e.getMessage());
    }

    /**
     * 멤버가 존재하지 않을 때
     * (찾지 못했을 떄)
     * 발생하는 예외입니다.
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<String> handleMemberNotFoundException(MemberNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(e.getMessage());
    }

    /**
     * 닉네임이 중복될 때 발생하는 예외입니다.
     *
     * @param e
     * @return
     */
    @ExceptionHandler(NickNameDuplicateException.class)
    public ResponseEntity<String> handleNickNameDuplicateException(NickNameDuplicateException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                             .body(e.getMessage());
    }

    /**
     * 팔로우 관계가 존재하지 않을 때 발생하는 예외입니다.
     *
     * @param e
     * @return
     */
    @ExceptionHandler(FollowShipNotFoundException.class)
    public ResponseEntity<String> handleFollowShipNotFoundException(FollowShipNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(e.getMessage());
    }



}
