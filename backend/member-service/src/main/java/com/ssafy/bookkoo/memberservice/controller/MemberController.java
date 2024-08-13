package com.ssafy.bookkoo.memberservice.controller;

import com.ssafy.bookkoo.memberservice.dto.request.RequestCertificationDto;
import com.ssafy.bookkoo.memberservice.client.dto.request.RequestLoginDto;
import com.ssafy.bookkoo.memberservice.dto.request.RequestRegisterMemberDto;
import com.ssafy.bookkoo.memberservice.client.dto.response.ResponseLoginTokenDto;
import com.ssafy.bookkoo.memberservice.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members/register")
public class MemberController {

    private final MemberService memberService;
    private final Duration REFRESH_TOKEN_EXPIRATION = Duration.ofDays(30);

    /**
     * 회원가입에 필요한 정보를 모두 받아서 회원가입합니다. 1. email 2. password (Optional : 소셜의 경우 사용 X) 3. nickName 4.
     * year 5. Gender (Enum) 6. categories Integer[] 7. introduction (Optional) 8. profileImg
     * (Optional) 9. SocialType (default = bookkoo)
     */

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(description = "회원 가입을 통해 새로운 유저를 등록합니다.", summary = "회원가입")
    public ResponseEntity<ResponseLoginTokenDto> register(
        @Valid @RequestPart("requestRegisterMemberDto") RequestRegisterMemberDto requestRegisterMemberDto,
        @RequestPart(value = "profileImg", required = false) MultipartFile profileImg,
        HttpServletResponse response
    ) {
        RequestLoginDto loginDto = memberService.register(requestRegisterMemberDto, profileImg);
        ResponseLoginTokenDto tokenDto = memberService.registerLogin(loginDto);
        Cookie cookie = new Cookie("refresh_token", tokenDto.refreshToken());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int) REFRESH_TOKEN_EXPIRATION.getSeconds());
        response.addCookie(cookie);
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/validation")
    @Operation(description = "이메일을 통해 인증 번호를 발송합니다.", summary = "이메일 인증 번호 발송")
    public ResponseEntity<HttpStatus> sendEmailValidation(
        @RequestBody String email
    ) {
        log.info("이메일 email로 인증번호 전송 : {}", email);
        memberService.sendCertiNumToEmail(email);
        return ResponseEntity.ok()
                             .build();
    }

    @GetMapping("/validation")
    @Operation(description = "이메일로 발송된 인증번호를 검증합니다.", summary = "인증번호 검증")
    public ResponseEntity<HttpStatus> checkEmailValidation(
        @Valid RequestCertificationDto requestCertificationDto
    ) {
        memberService.checkValidationEmail(requestCertificationDto);
        return ResponseEntity.ok()
                             .build();
    }

    @GetMapping("/duplicate/email")
    @Operation(description = "이메일을 받아 중복 체크를 합니다.", summary = "이메일 중복 체크")
    public ResponseEntity<HttpStatus> checkDuplicateEmail(
        @RequestParam(name = "email") String email
    ) {
        memberService.checkDuplEmail(email);
        return ResponseEntity.ok()
                             .build();
    }

    @GetMapping("/duplicate/name")
    @Operation(description = "닉네임을 받아 중복 체크를 합니다.", summary = "닉네임 중복 체크")
    public ResponseEntity<HttpStatus> checkDuplicateNickName(
        @RequestParam(name = "name") String nickName
    ) {
        memberService.checkDuplNickName(nickName);
        return ResponseEntity.ok()
                             .build();
    }

    @PostMapping("/password/reset")
    @Operation(description = "가입한 이메일에 대한 비밀번호를 초기화 합니다.", summary = "비밀번호 초기화")
    public ResponseEntity<HttpStatus> passwordReset(
        @RequestBody String email
    ) {
        memberService.passwordReset(email);
        return ResponseEntity.ok()
                             .build();
    }

}
