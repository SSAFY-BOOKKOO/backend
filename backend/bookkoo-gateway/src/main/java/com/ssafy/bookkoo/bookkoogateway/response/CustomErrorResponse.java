package com.ssafy.bookkoo.bookkoogateway.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public class CustomErrorResponse {

    private HttpStatusCode status;
    private String message;

}
