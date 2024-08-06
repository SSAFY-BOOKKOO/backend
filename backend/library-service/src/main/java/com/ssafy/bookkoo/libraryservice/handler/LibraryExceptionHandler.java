package com.ssafy.bookkoo.libraryservice.handler;

import com.ssafy.bookkoo.libraryservice.exception.BookAlreadyMappedException;
import com.ssafy.bookkoo.libraryservice.exception.LibraryBookNotFoundException;
import com.ssafy.bookkoo.libraryservice.exception.LibraryLimitExceededException;
import com.ssafy.bookkoo.libraryservice.exception.LibraryNotFoundException;
import com.ssafy.bookkoo.libraryservice.exception.MemberNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * 서재 관련 예외를 처리하는 핸들러 클래스입니다.
 */
@RestControllerAdvice
public class LibraryExceptionHandler {

    /**
     * 없는 서재를 조회할 때 발생하는 예외 핸들러
     *
     * @param e LibraryNotFoundException 예외 객체
     * @return 에러 메시지와 상태 코드
     */
    @ExceptionHandler(LibraryNotFoundException.class)
    public ResponseEntity<String> libraryNotFoundException(LibraryNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(e.getMessage());
    }

    /**
     * 책이 이미 서재에 매핑되어 있을 때 발생하는 예외 핸들러
     *
     * @param e BookAlreadyMappedException 예외 객체
     * @return 에러 메시지와 상태 코드
     */
    @ExceptionHandler(BookAlreadyMappedException.class)
    public ResponseEntity<String> bookAlreadyMappedException(BookAlreadyMappedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(e.getMessage());
    }

    /**
     * 멤버를 찾을 수 없을 때 발생하는 예외 핸들러
     *
     * @param e MemberNotFoundException 예외 객체
     * @return 에러 메시지와 상태 코드
     */
    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<String> memberNotFoundException(MemberNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(e.getMessage());
    }

    /**
     * 메서드 인수 유형 불일치 예외 핸들러
     *
     * @param ex MethodArgumentTypeMismatchException 예외 객체
     * @return 에러 메시지와 상태 코드
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = String.format("잘못된 값 : '%s' 을 입력하였습니다 (for parameter '%s')", ex.getValue(),
            ex.getName());
        return ResponseEntity.badRequest()
                             .body(message);
    }

    /**
     * 서재 ID, Book ID 매퍼에 없을 때
     *
     * @param e LibraryBookNotFoundException 예외 객체
     * @return 에러 메시지와 상태 코드
     */
    @ExceptionHandler(LibraryBookNotFoundException.class)
    public ResponseEntity<String> handleLibraryBookNotFoundException(LibraryBookNotFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(e.getMessage());
    }

    /**
     * 서재 개수 한계에 도달했는데 추가하려고 할때 최대 한도를
     *
     * @param e LibraryLimitExceededException 예외 객체
     * @return 에러 메시지와 상태 코드
     */
    @ExceptionHandler(LibraryLimitExceededException.class)
    public ResponseEntity<String> handleLibraryLimitExceededException(LibraryLimitExceededException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(e.getMessage());
    }
}
