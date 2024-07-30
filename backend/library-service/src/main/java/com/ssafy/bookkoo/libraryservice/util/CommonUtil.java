package com.ssafy.bookkoo.libraryservice.util;

import org.springframework.http.HttpHeaders;

public class CommonUtil {

    public static Long getMemberId(HttpHeaders headers) {
        return Long.valueOf(headers.get("member-passport")
                                   .get(0));
    }
}
