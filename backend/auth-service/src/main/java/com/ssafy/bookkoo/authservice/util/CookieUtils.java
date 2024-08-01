package com.ssafy.bookkoo.authservice.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import org.springframework.util.SerializationUtils;

@Service
@RequiredArgsConstructor
public class CookieUtils {

    public static final Duration REFRESH_TOKEN_EXPIRATION = Duration.ofDays(30);

    public static Cookie secureCookieGenerate(String key, String value, Duration expiration) {
        Cookie cookie = new Cookie(key, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int) expiration.getSeconds());
        return cookie;
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response,
        String name) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
    }

    /**
     * 쿠키 객체 직렬화
     *
     * @param obj
     * @return
     */
    public static String serialize(Object obj) {
        return Base64.getUrlEncoder()
                     .encodeToString(SerializationUtils.serialize(obj));
    }

    /**
     * 쿠키 객체 역직렬화
     * @param cookie
     * @param cls
     * @return
     * @param <T>
     */
    public static <T> T deserialize(Cookie cookie, Class<T> cls) {
        return cls.cast(
            SerializationUtils.deserialize(
                Base64.getUrlDecoder()
                      .decode(cookie.getValue())
            )
        );
    }
}
