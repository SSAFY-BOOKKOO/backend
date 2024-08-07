package com.ssafy.bookkoo.bookservice.handler;

import com.ssafy.bookkoo.bookservice.exception.BookCreateFailedException;
import com.ssafy.bookkoo.bookservice.exception.BookNotFoundException;
import com.ssafy.bookkoo.bookservice.exception.CategoryNotFoundException;
import com.ssafy.bookkoo.bookservice.exception.InvalidAttributeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

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
     * 없는 카테고리 조회 때 발생
     *
     * @param e : error
     * @return error message + status
     */
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<String> categoryNotFoundException(CategoryNotFoundException e) {
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

    /**
     * 엔티티에 없는 필드에 대해 요청할 때 발생하는 예외
     *
     * @param e : execption
     * @return string
     */
    @ExceptionHandler(InvalidAttributeException.class)
    public ResponseEntity<String> handleInvalidAttributeException(InvalidAttributeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(e.getMessage());
    }

    /**
     * dto의 valid 옵션인 것에 에러 발생시
     *
     * @param ex      : exception
     * @param request : 요청
     * @return : string
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(
        IllegalArgumentException ex,
        WebRequest request
    ) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * 책 생성 중 에러 발생
     *
     * @param e BookCreateFailedException
     * @return 에러코드 + 설명
     */
    @ExceptionHandler(BookCreateFailedException.class)
    public ResponseEntity<String> handleBookCreateFailedException(BookCreateFailedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(e.getMessage());
    }
}
