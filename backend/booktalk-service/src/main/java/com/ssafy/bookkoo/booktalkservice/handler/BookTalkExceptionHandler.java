package com.ssafy.bookkoo.booktalkservice.handler;

import com.ssafy.bookkoo.booktalkservice.exception.BookTalkAlreadyExistException;
import com.ssafy.bookkoo.booktalkservice.exception.BookTalkNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BookTalkExceptionHandler {

    @ExceptionHandler(BookTalkAlreadyExistException.class)
    public ResponseEntity<String> handleBookTalkAlreadyExistException(
        BookTalkAlreadyExistException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                             .body(e.getMessage());
    }

    @ExceptionHandler(BookTalkNotFoundException.class)
    public ResponseEntity<String> handleBookTalkNotFoundException(
        BookTalkNotFoundException e
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(e.getMessage());
    }

}
