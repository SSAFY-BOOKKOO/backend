package com.ssafy.bookkoo.memberservice.controller;

import com.ssafy.bookkoo.memberservice.dto.RequestAdditionalInfo;
import com.ssafy.bookkoo.memberservice.dto.RequestCertificationDto;
import com.ssafy.bookkoo.memberservice.dto.RequestRegisterDto;
import com.ssafy.bookkoo.memberservice.exception.EmailDuplicateException;
import com.ssafy.bookkoo.memberservice.exception.NickNameDuplicateException;
import com.ssafy.bookkoo.memberservice.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members/register")
public class MemberController {

    private final MemberService memberService;

    /**
     * 등록 시 등록된 memberId 반환
     *
     * @param requestRegisterDto
     * @return
     */
    @PostMapping("/email")
    @Operation(description = "회원가입을 통해 새로운 유저를 등록합니다.", summary = "회원가입")
    public ResponseEntity<String> register(
        @Valid @RequestBody RequestRegisterDto requestRegisterDto
    ) {
        String memberId = memberService.register(requestRegisterDto);
        return ResponseEntity.ok(memberId);
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
        RequestCertificationDto requestCertificationDto
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
        if (!memberService.checkDuplEmail(email)) {
            throw new EmailDuplicateException();
        }
        return ResponseEntity.ok()
                             .build();
    }

    @GetMapping("/duplicate/name")
    @Operation(description = "닉네임을 받아 중복 체크를 합니다.", summary = "닉네임 중복 체크")
    public ResponseEntity<HttpStatus> checkDuplicateNickName(
        @RequestParam(name = "name") String nickName
    ) {
        if (!memberService.checkDuplNickName(nickName)) {
            throw new NickNameDuplicateException();
        }
        return ResponseEntity.ok()
                             .build();
    }

    @PostMapping("/info")
    @Operation(description = "memberId, 닉네임, 카테고리, 출생년도, 소개글을 받아 추가정보를 저장합니다.", summary = "추가 정보 저장")
    public ResponseEntity<String> registerAdditionalInfo(
        @Valid @RequestPart RequestAdditionalInfo requestAdditionalInfo,
        @RequestPart("profileImg") MultipartFile profileImg
    ) {
        String filekey = memberService.registerAdditionalInfo(requestAdditionalInfo, profileImg);
        return ResponseEntity.ok(filekey);
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
