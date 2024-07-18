package com.ssafy.bookkoo.memberservice.controller;

import com.ssafy.bookkoo.memberservice.dto.RequestCertificationDto;
import com.ssafy.bookkoo.memberservice.dto.RequestRegisterDto;
import com.ssafy.bookkoo.memberservice.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
//해당 prefix경로로 들어온 요청을 처리해야하기 때문에
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    @Operation(description = "회원가입")
    public ResponseEntity<HttpStatus> register(
        @RequestBody RequestRegisterDto requestRegisterDto
    ) {
        memberService.register(requestRegisterDto);
        return ResponseEntity.ok()
                             .build();
    }

    @PostMapping("/register/validation")
    public ResponseEntity<HttpStatus> sendEmailValidation(
        @RequestBody String email
    ) {
        log.info("email : {}", email);
        memberService.sendCertiNumToEmail(email);
        return ResponseEntity.ok()
                             .build();
    }

    @GetMapping("/register/validation")
    public ResponseEntity<HttpStatus> checkEmailValidation(
        RequestCertificationDto requestCertificationDto
    ) {
        log.info("data : {}", requestCertificationDto);

        memberService.checkValidationEmail(requestCertificationDto);
        return ResponseEntity.ok()
                             .build();
    }
}
