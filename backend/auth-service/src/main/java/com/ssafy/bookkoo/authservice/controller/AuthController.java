package com.ssafy.bookkoo.authservice.controller;

import com.ssafy.bookkoo.authservice.dto.RequestLoginDto;
import com.ssafy.bookkoo.authservice.dto.ResponseLoginTokenDto;
import com.ssafy.bookkoo.authservice.exception.TokenExpiredException;
import com.ssafy.bookkoo.authservice.service.AuthService;
import com.ssafy.bookkoo.authservice.util.CookieGenerator;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final CookieGenerator cookieGenerator;
    private static final String REFRESH_TOKEN_NAME = "refresh_token";

    @PostMapping("/login/email")
    @Operation(description = "이메일과 비밀번호를 통해 로그인 합니다.", summary = "로그인")
    public ResponseEntity<ResponseLoginTokenDto> login(
        @RequestBody RequestLoginDto requestLoginDto,
        HttpServletResponse response
    ) {
        ResponseLoginTokenDto responseLoginTokenDto = authService.login(requestLoginDto);

        Cookie secureCookie = cookieGenerator.secureCookieGenerate(REFRESH_TOKEN_NAME, responseLoginTokenDto.refreshToken(),
            CookieGenerator.REFRESH_TOKEN_EXPIRATION);

        response.addCookie(secureCookie);

        return ResponseEntity.ok(responseLoginTokenDto);
    }

    @PostMapping("/token")
    public ResponseEntity<ResponseLoginTokenDto> getToken(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        Optional<Cookie> optionalRefreshToken = Arrays.stream(request.getCookies())
                                              .filter(cookie -> cookie.getName()
                                                                      .equals(REFRESH_TOKEN_NAME))
                                              .findFirst();

        Cookie cookie = optionalRefreshToken.orElseThrow(TokenExpiredException::new);

        ResponseLoginTokenDto responseLoginTokenDto = authService.getTokenDto(cookie.getValue());

        Cookie secureCookie = cookieGenerator.secureCookieGenerate(REFRESH_TOKEN_NAME, responseLoginTokenDto.refreshToken(),
            CookieGenerator.REFRESH_TOKEN_EXPIRATION);

        response.addCookie(secureCookie);
        return ResponseEntity.ok(responseLoginTokenDto);
    }

}
