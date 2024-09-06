package com.ssafy.bookkoo.curationservice.handler;

import com.ssafy.bookkoo.curationservice.exception.CurationNotFoundException;
import com.ssafy.bookkoo.curationservice.exception.DtoValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CurationExceptionHandler {

    @ExceptionHandler(CurationNotFoundException.class)
    public ResponseEntity<String> curationNotFoundException(CurationNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(e.getMessage());
    }

    @ExceptionHandler(DtoValidationException.class)
    public ResponseEntity<String> dtoValidationException(DtoValidationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(e.getMessage());
    }
}
