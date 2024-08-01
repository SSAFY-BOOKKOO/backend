package com.ssafy.bookkoo.bookkoogateway.response;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class CustomErrorResponseWithData<T> extends CustomErrorResponse {

    private final T data;

    public CustomErrorResponseWithData(HttpStatusCode status, String message) {
        this(status, message, null);
    }

    public CustomErrorResponseWithData(HttpStatusCode status, String message, T data) {
        super(status, message);
        this.data = data;
    }
}
