package com.ssafy.bookkoo.authservice.controller;

import com.ssafy.bookkoo.authservice.dto.RequestLoginDto;
import com.ssafy.bookkoo.authservice.dto.ResponseLoginTokenDto;
import com.ssafy.bookkoo.authservice.exception.TokenExpiredException;
import com.ssafy.bookkoo.authservice.service.AuthService;
import com.ssafy.bookkoo.authservice.util.CommonUtil;
import com.ssafy.bookkoo.authservice.util.CookieUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private static final String REFRESH_TOKEN_NAME = "refresh_token";

    @PostMapping("/login/email")
    @Operation(summary = "로그인", description = "이메일과 비밀번호를 통해 로그인 합니다.")
    public ResponseEntity<ResponseLoginTokenDto> login(
        @RequestBody RequestLoginDto requestLoginDto,
        HttpServletResponse response
    ) {
        ResponseLoginTokenDto responseLoginTokenDto = authService.login(requestLoginDto);

        Cookie secureCookie = CookieUtils.secureCookieGenerate(REFRESH_TOKEN_NAME,
            responseLoginTokenDto.refreshToken(),
            CookieUtils.REFRESH_TOKEN_EXPIRATION);

        response.addCookie(secureCookie);

        return ResponseEntity.ok(responseLoginTokenDto);
    }

    @PostMapping("/token")
    @Operation(summary = "새로운 리프레시 토큰도 함께 발급합니다.",
        description = "쿠키(헤더)에 포함된 리프레시 토큰을 통해 액세스 토큰을 재발급합니다.")
    public ResponseEntity<ResponseLoginTokenDto> getToken(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        Optional<Cookie> optionalRefreshToken = Arrays.stream(request.getCookies())
                                                      .filter(cookie -> cookie.getName()
                                                                              .equals(
                                                                                  REFRESH_TOKEN_NAME))
                                                      .findFirst();

        Cookie cookie = optionalRefreshToken.orElseThrow(TokenExpiredException::new);

        ResponseLoginTokenDto responseLoginTokenDto = authService.getTokenDto(cookie.getValue());

        Cookie secureCookie = CookieUtils.secureCookieGenerate(REFRESH_TOKEN_NAME,
            responseLoginTokenDto.refreshToken(),
            CookieUtils.REFRESH_TOKEN_EXPIRATION);

        response.addCookie(secureCookie);
        return ResponseEntity.ok(responseLoginTokenDto);
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃 API", description = "멤버가 로그아웃합니다.")
    public ResponseEntity<HttpStatus> logout(
        @RequestHeader HttpHeaders headers,
        HttpServletResponse response
    ) {
        Long memberId = CommonUtil.getMemberId(headers);
        authService.logout(memberId);
        //쿠키 만료 시간 0으로 설정
        Cookie cookie = CookieUtils.secureCookieGenerate(REFRESH_TOKEN_NAME, "", Duration.ZERO);
//        cookie.setDomain("ssafy.io");
        response.addCookie(cookie);
        return ResponseEntity.ok()
                             .build();
    }


    @GetMapping("/token/develop")
    @Operation(summary = "기본 유저를 통해 토큰을 발급합니다.",
        description = "개발용 토큰 발급 API입니다. 기본 유저 (test@test/test123$)에 대한 토큰을 발급합니다.")
    public ResponseEntity<ResponseLoginTokenDto> forDeveloperToken(
        HttpServletResponse response
    ) {
        ResponseLoginTokenDto responseLoginTokenDto = authService.getDeveloperTokenDto();
        Cookie secureCookie = CookieUtils.secureCookieGenerate(REFRESH_TOKEN_NAME,
            responseLoginTokenDto.refreshToken(),
            CookieUtils.REFRESH_TOKEN_EXPIRATION);

        response.addCookie(secureCookie);
        return ResponseEntity.ok(responseLoginTokenDto);
    }
}
