package com.ssafy.bookkoo.authservice.controller;

import com.ssafy.bookkoo.authservice.dto.RequestLoginDto;
import com.ssafy.bookkoo.authservice.dto.ResponseLoginTokenDto;
import com.ssafy.bookkoo.authservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(description = "이메일과 비밀번호를 통해 로그인 합니다.", summary = "로그인")
    public ResponseEntity<ResponseLoginTokenDto> login(
        @RequestBody RequestLoginDto requestLoginDto,
        HttpServletResponse response
    ) {
        ResponseLoginTokenDto responseLoginTokenDto = authService.login(requestLoginDto);

        Cookie cookie = new Cookie("refresh_token", responseLoginTokenDto.refreshToken());
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(7 * 24 * 60 * 60); //7일
        response.addCookie(cookie);

        return ResponseEntity.ok(responseLoginTokenDto);
    }
}
