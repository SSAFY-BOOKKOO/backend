package com.ssafy.bookkoo.commonservice.email.controller;

import com.ssafy.bookkoo.commonservice.email.dto.request.RequestSendEmailDto;
import com.ssafy.bookkoo.commonservice.email.service.MailSendService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/commons/email")
public class EmailSendController {

    private final MailSendService mailSendService;

    @PostMapping
    @Operation(summary = "이메일 전송 API",
        description = "제목, 컨텐츠, 수신자(들)을 받아 이메일을 전송합니다.")
    public ResponseEntity<HttpStatus> sendEmail(
        @RequestBody RequestSendEmailDto sendEmailDto
    ) {
        mailSendService.sendMail(sendEmailDto);
        return ResponseEntity.ok()
                             .build();
    }
}
