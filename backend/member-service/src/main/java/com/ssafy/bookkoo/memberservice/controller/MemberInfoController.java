package com.ssafy.bookkoo.memberservice.controller;

import com.ssafy.bookkoo.memberservice.dto.RequestUpdatePasswordDto;
import com.ssafy.bookkoo.memberservice.dto.ResponseMemberInfoDto;
import com.ssafy.bookkoo.memberservice.service.MemberInfoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members/info")
public class MemberInfoController {

    private final MemberInfoService memberInfoService;

    @GetMapping
    public ResponseEntity<ResponseMemberInfoDto> getMemberInfo(
        @RequestParam(name = "memberId") String memberId
    ) {
        ResponseMemberInfoDto memberInfo = memberInfoService.getMemberInfo(memberId);
        return ResponseEntity.ok(memberInfo);
    }

    @PostMapping("/update/password")
    public ResponseEntity<HttpStatus> updatePassword(
        @Valid @RequestBody RequestUpdatePasswordDto requestUpdatePasswordDto
    ) {
        memberInfoService.updatePassword(requestUpdatePasswordDto);
        return ResponseEntity.ok()
                             .build();
    }
}
