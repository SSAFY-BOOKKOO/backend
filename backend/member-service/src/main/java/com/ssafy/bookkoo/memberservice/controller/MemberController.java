package com.ssafy.bookkoo.memberservice.controller;

import com.ssafy.bookkoo.memberservice.dto.RequestCertificationDto;
import com.ssafy.bookkoo.memberservice.dto.RequestRegisterDto;
import com.ssafy.bookkoo.memberservice.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    @Operation(description = "회원가입을 통해 새로운 유저를 등록합니다.", summary = "회원가입")
    public ResponseEntity<HttpStatus> register(
        @Valid @RequestBody RequestRegisterDto requestRegisterDto
    ) {
        memberService.register(requestRegisterDto);
        return ResponseEntity.ok()
                             .build();
    }

    @PostMapping("/register/validation")
    @Operation(description = "이메일을 통해 인증 번호를 발송합니다.", summary = "이메일 인증 번호 발송")
    public ResponseEntity<HttpStatus> sendEmailValidation(
        @RequestBody String email
    ) {
        log.info("email : {}", email);
        memberService.sendCertiNumToEmail(email);
        return ResponseEntity.ok()
                             .build();
    }

    @GetMapping("/register/validation")
    @Operation(description = "이메일로 발송된 인증번호를 검증합니다.", summary = "인증번호 검증")
    public ResponseEntity<HttpStatus> checkEmailValidation(
        RequestCertificationDto requestCertificationDto
    ) {
        log.info("data : {}", requestCertificationDto);

        memberService.checkValidationEmail(requestCertificationDto);
        return ResponseEntity.ok()
                             .build();
    }

    //TODO: 닉네임 중복 체크 API개발
    //TODO: 추가 정보 저장 API개발

}
