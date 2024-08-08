package com.ssafy.bookkoo.bookservice.handler;

import com.ssafy.bookkoo.bookservice.exception.ReviewDeleteFailedException;
import com.ssafy.bookkoo.bookservice.exception.ReviewHasWrittenException;
import com.ssafy.bookkoo.bookservice.exception.ReviewIsNotYoursException;
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

    /**
     * 이미 작성된 리뷰가 있을 때 작성 시도 시 발생
     *
     * @param e ReviewHasWrittenException
     * @return status 코드 + 설명
     */
    @ExceptionHandler(ReviewHasWrittenException.class)
    public ResponseEntity<String> handleReviewHasWrittenException(ReviewHasWrittenException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(e.getMessage());
    }

    /**
     * 내 리뷰가 아닌걸 조작하려고 할 때 발생
     *
     * @param e ReviewIsNotYoursException
     * @return status 코드 + 설명
     */
    @ExceptionHandler(ReviewIsNotYoursException.class)
    public ResponseEntity<String> handleReviewIsNotYoursException(ReviewIsNotYoursException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(e.getMessage());
    }

    /**
     * 리뷰 삭제 실패시 발생
     *
     * @param e ReviewDeleteFailedException
     * @return status 코드 + 설명
     */
    @ExceptionHandler(ReviewDeleteFailedException.class)
    public ResponseEntity<String> handleReviewDeleteFailedException(ReviewDeleteFailedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(e.getMessage());
    }
}
