package com.ssafy.bookkoo.curationservice.handler;

import com.ssafy.bookkoo.curationservice.exception.MessageParsingException;
import com.ssafy.bookkoo.curationservice.exception.OpenAiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OpenAiExceptionHandler {

    @ExceptionHandler(OpenAiException.class)
    public ResponseEntity<String> handleOpenAiException(OpenAiException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(e.getMessage());
    }

    @ExceptionHandler(MessageParsingException.class)
    public ResponseEntity<String> handleMessageParsingException(MessageParsingException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(e.getMessage());
    }

}
