package com.ssafy.bookkoo.bookservice.handler;

import com.ssafy.bookkoo.bookservice.exception.BookNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    /**
     * enum 타입에 잘못된 값을 넣으면 나오는 에러
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = "Invalid params value.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body("{\"error\":\"" + errorMessage + "\"}");
    }
}
