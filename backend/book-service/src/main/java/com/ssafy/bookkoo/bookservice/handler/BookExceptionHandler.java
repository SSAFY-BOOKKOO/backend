package com.ssafy.bookkoo.bookservice.handler;

import com.ssafy.bookkoo.bookservice.exception.BookNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BookExceptionHandler {

    /**
     * 없는 책을 조회할 때 발생하는 예외 핸들러
     *
     * @param e : BookNotFoundException
     * @return 에러메시지
     */
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<String> bookNotFoundException(BookNotFoundException e) {
        return ResponseEntity.status(404)
                             .body(e.getMessage());
    }
}
