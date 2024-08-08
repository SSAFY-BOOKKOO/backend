package com.ssafy.bookkoo.booktalkservice.handler;

import com.ssafy.bookkoo.booktalkservice.exception.BookNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BookExceptionHandler {
    
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<String> bookNotFoundException(BookNotFoundException e) {
        return ResponseEntity.status(404)
                             .body(e.getMessage());
    }

}
