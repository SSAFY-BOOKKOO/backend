package com.ssafy.bookkoo.notificationservice.handler;

import com.ssafy.bookkoo.notificationservice.exception.NotificationNotFoundException;
import com.ssafy.bookkoo.notificationservice.exception.UnAuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class NotificationExceptionHandler {

    /**
     * 알림이 존재하지 않을 때 발생하는 예외입니다.
     *
     * @param e
     * @return
     */
    @ExceptionHandler(NotificationNotFoundException.class)
    public ResponseEntity<String> handleNotificationNotFoundException(NotificationNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(e.getMessage());
    }
    /**
     * 해당 알림에 대한 권한이 존재하지 않을 떄 발생하는 예외입니다.
     *
     * @param e
     * @return
     */
    @ExceptionHandler(UnAuthorizationException.class)
    public ResponseEntity<String> handleUnAuthorizationException(UnAuthorizationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body(e.getMessage());
    }
}
