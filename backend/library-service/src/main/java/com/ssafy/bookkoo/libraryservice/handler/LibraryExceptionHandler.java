package com.ssafy.bookkoo.libraryservice.handler;

import com.ssafy.bookkoo.libraryservice.exception.BookAlreadyMappedException;
import com.ssafy.bookkoo.libraryservice.exception.BookClientException;
import com.ssafy.bookkoo.libraryservice.exception.LibraryBookLimitExceededException;
import com.ssafy.bookkoo.libraryservice.exception.LibraryBookNotFoundException;
import com.ssafy.bookkoo.libraryservice.exception.LibraryIsNotYoursException;
import com.ssafy.bookkoo.libraryservice.exception.LibraryLimitExceededException;
import com.ssafy.bookkoo.libraryservice.exception.LibraryMoveToSameLibraryException;
import com.ssafy.bookkoo.libraryservice.exception.LibraryNotFoundException;
import com.ssafy.bookkoo.libraryservice.exception.MemberNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
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

    /**
     * 서재가 내 게 아닌데 삭제하려고 할 때
     *
     * @param e LibraryIsNotYoursException
     * @return 에러 메시지 + 상태 코드
     */
    @ExceptionHandler(LibraryIsNotYoursException.class)
    public ResponseEntity<String> handleLibraryIsNotYoursException(LibraryIsNotYoursException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(e.getMessage());
    }

    /**
     * 서재에 최대 책 개수를 넣었을 떄
     *
     * @param e LibraryBookLimitExceededException
     * @return 에러 메시지 + 상태 코드
     */
    @ExceptionHandler(LibraryBookLimitExceededException.class)
    public ResponseEntity<String> handleLibraryBookLimitExceededException(
        LibraryBookLimitExceededException e
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(e.getMessage());
    }

    /**
     * 같은 서재로 옮기려고 할 떄
     *
     * @param e LibraryMoveToSameLibraryException
     * @return 에러 메시지 + 상태 코드
     */
    @ExceptionHandler(LibraryMoveToSameLibraryException.class)
    public ResponseEntity<String> handleLibraryMoveToSameLibraryException(
        LibraryMoveToSameLibraryException e
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(e.getMessage());
    }

    /**
     * 파라미터 valid 에 벗어나는 잘못된 값을 넣으면 나오는 에러
     *
     * @param ex MethodArgumentNotValidException
     * @return 에러코드 + 에러설명
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // 모든 에러를 가져와서 메시지 구성
        List<String> errorMessages = ex.getBindingResult()
                                       .getFieldErrors()
                                       .stream()
                                       .map(fieldError -> String.format(
                                           "Field '%s': %s",
                                           fieldError.getField(),
                                           fieldError.getDefaultMessage()
                                       ))
                                       .toList();

        // 에러 메시지들을 JSON 형식의 배열로 반환
        String errorMessageJson = errorMessages.stream()
                                               .map(message -> "\"" + message + "\"")
                                               .collect(Collectors.joining(", ", "[", "]"));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(errorMessageJson);
    }

    /**
     * 북 클라이언트 통신 중 에러 처리
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BookClientException.class)
    public ResponseEntity<String> handleBookClientException(BookClientException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(e.getMessage());
    }
}
