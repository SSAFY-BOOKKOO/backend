package com.ssafy.bookkoo.bookservice.handler;

import com.ssafy.bookkoo.bookservice.exception.ReviewNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 리뷰 관련 예외를 처리하는 핸들러 클래스입니다.
 */
@RestControllerAdvice
public class ReviewExceptionHandler {

    /**
     * ReviewNotFoundException이 발생할 때 처리하는 메서드입니다.
     *
     * @param e ReviewNotFoundException 예외 객체
     * @return HTTP 404 상태 코드와 예외 메시지를 포함한 응답 엔티티
     */
    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<String> handleReviewNotFoundException(ReviewNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(e.getMessage());
    }
}
