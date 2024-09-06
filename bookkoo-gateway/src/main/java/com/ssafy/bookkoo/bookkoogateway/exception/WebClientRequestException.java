package com.ssafy.bookkoo.bookkoogateway.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

/**
 * 웹 클라이언트 요청 에러
 */
public class WebClientRequestException extends ResponseStatusException {

    public WebClientRequestException(HttpStatusCode status) {
        super(status);
    }

    @Override
    public String getMessage() {
        return "서비스 요청에 실패했습니다.";
    }
}
