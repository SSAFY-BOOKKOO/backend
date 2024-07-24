package com.ssafy.bookkoo.authservice.util;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class CookieGenerator {

    public static final Duration REFRESH_TOKEN_EXPIRATION = Duration.ofDays(7);

    public Cookie secureCookieGenerate(String key, String value, Duration expiration) {
        Cookie cookie = new Cookie(key, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int) expiration.getSeconds());
        return cookie;
    }
}
