package com.ssafy.bookkoo.libraryservice.handler;

import com.ssafy.bookkoo.libraryservice.exception.BookAlreadyMappedException;
import com.ssafy.bookkoo.libraryservice.exception.LibraryNotFoundException;
import com.ssafy.bookkoo.libraryservice.exception.MemberNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LibraryExceptionHandler {

    /**
     * 없는 책을 조회할 때 발생하는 예외 핸들러
     *
     * @param e : BookNotFoundException
     * @return 에러메시지
     */
    @ExceptionHandler(LibraryNotFoundException.class)
    public ResponseEntity<String> bookNotFoundException(LibraryNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(e.getMessage());
    }

    @ExceptionHandler(BookAlreadyMappedException.class)
    public ResponseEntity<String> illegalStateException(BookAlreadyMappedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(e.getMessage());
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<String> memberNotFoundException(MemberNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(e.getMessage());
    }
}
