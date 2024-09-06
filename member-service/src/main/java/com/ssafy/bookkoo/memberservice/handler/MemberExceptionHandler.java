package com.ssafy.bookkoo.memberservice.handler;

import com.ssafy.bookkoo.memberservice.exception.*;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MemberExceptionHandler {

    /**
     * 이메일 인증에 실패했을 때 발생하는 예외입니다.
     *
     * @param e
     * @return
     */
    @ExceptionHandler(EmailNotValidException.class)
    public ResponseEntity<String> handleEmailNotValidException(EmailNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(e.getMessage());
    }

    /**
     * 이메일이 중복될 때 발생하는 예외입니다.
     *
     * @param e
     * @return
     */
    @ExceptionHandler(EmailDuplicateException.class)
    public ResponseEntity<String> handleEmailDuplicateException(EmailDuplicateException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                             .body(e.getMessage());
    }

    /**
     * 이메일 전송에 실패했을 때 발생하는 예외입니다.
     *
     * @param e
     * @return
     */
    @ExceptionHandler(EmailSendFailException.class)
    public ResponseEntity<String> handleEmailSendFailException(EmailSendFailException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(e.getMessage());
    }

    /**
     * 멤버가 존재하지 않을 때 (찾지 못했을 떄) 발생하는 예외입니다.
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<String> handleMemberNotFoundException(MemberNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(e.getMessage());
    }

    /**
     * 닉네임이 중복될 때 발생하는 예외입니다.
     *
     * @param e
     * @return
     */
    @ExceptionHandler(NickNameDuplicateException.class)
    public ResponseEntity<String> handleNickNameDuplicateException(NickNameDuplicateException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                             .body(e.getMessage());
    }

    /**
     * 팔로우 관계가 존재하지 않을 때 발생하는 예외입니다.
     *
     * @param e
     * @return
     */
    @ExceptionHandler(FollowShipNotFoundException.class)
    public ResponseEntity<String> handleFollowShipNotFoundException(FollowShipNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(e.getMessage());
    }


    /**
     * 멤버가 아직 추가 정보를 입력하지 않았을 때 발생하는 예외입니다.
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MemberInfoNotExistException.class)
    public ResponseEntity<String> handleMemberInfoNotExistException(MemberInfoNotExistException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(e.getMessage());
    }

    /**
     * 패스워드 형식이 유효하지 않을 때 발생하는 예외입니다.
     *
     * @param e
     * @return
     */
    @ExceptionHandler(PasswordNotValidException.class)
    public ResponseEntity<String> handlePasswordNotValidException(PasswordNotValidException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                             .body(e.getMessage());
    }

    /**
     * 이미 팔로우한 상태에서 다시 요청했을 때 발생하는 예외
     *
     * @param e
     * @return
     */
    @ExceptionHandler(AlreadyFollowedException.class)
    public ResponseEntity<String> handleAlreadyFollowedException(AlreadyFollowedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                             .body(e.getMessage());
    }

    /**
     * 해당 글귀를 찾을 수 없을 때 발생하는 예외 입니다.
     *
     * @param e
     * @return
     */
    @ExceptionHandler(QuoteNotFoundException.class)
    public ResponseEntity<String> handleQuoteNotFoundException(QuoteNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(e.getMessage());
    }

    /**
     * 권한이 없는 리소스에 접근할 때 발생하는 예외
     *
     * @param e
     * @return
     */
    @ExceptionHandler(UnAuthorizationException.class)
    public ResponseEntity<String> handleUnAuthorizationException(UnAuthorizationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body(e.getMessage());
    }

    /**
     * OCR을 통해 추출한 텍스트가 없을 경우 발생하는 예외
     * @param e
     * @return
     */
    @ExceptionHandler(TextNotDetectedException.class)
    public ResponseEntity<String> handleTextNotDetectedException(TextNotDetectedException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(e.getMessage());
    }

    /**
     * 파일을 업로드 하지 않았을 경우 발생하는 예외
     * @param e
     * @return
     */
    @ExceptionHandler(FileNotExistException.class)
    public ResponseEntity<String> handleFileNotExistException(FileNotExistException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(e.getMessage());
    }

    /**
     * OCR을 수행 중 에러 발생 시 발생하는 예외
     * @param e
     * @return
     */
    @ExceptionHandler(OCRProcessingException.class)
    public ResponseEntity<String> handleOCRProcessingException(OCRProcessingException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(e.getMessage());
    }

    /**
     * OCR을 수행 중 에러 발생 시 발생하는 예외
     * @param e
     * @return
     */
    @ExceptionHandler(ImageUploadException.class)
    public ResponseEntity<String> handleImageUploadException(ImageUploadException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(e.getMessage());
    }

    /**
     * 알림 생성 오류 (타 서비스 호출에서 예외 발생)
     * @param e
     * @return
     */
    @ExceptionHandler(CreateNotificationFailException.class)
    public ResponseEntity<String> handleCreateNotificationFailException(CreateNotificationFailException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(e.getMessage());
    }

    /**
     * 서재 생성 오류 (타 서비스 호출에서 예외 발생)
     * @param e
     * @return
     */
    @ExceptionHandler(CreateLibraryFailException.class)
    public ResponseEntity<String> handleCreateLibraryFailException(CreateLibraryFailException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(e.getMessage());
    }

    /**
     * 서재 삭제 과정에서 예외 발생
     * @param e
     * @return
     */
    @ExceptionHandler(DeleteFailException.class)
    public ResponseEntity<String> handleDeleteLibrariesFailException(DeleteFailException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(e.getMessage());
    }

    /**
     * 파라미터 valid 에 벗어나는 잘못된 값을 넣으면 나오는 에러
     *
     * @param e MethodArgumentNotValidException
     * @return 에러코드 + 에러설명
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException e) {
        // 모든 에러를 가져와서 메시지 구성
        List<String> errorMessages = e.getBindingResult()
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


}
