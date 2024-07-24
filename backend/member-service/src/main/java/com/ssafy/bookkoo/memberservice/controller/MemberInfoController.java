package com.ssafy.bookkoo.memberservice.controller;

import com.ssafy.bookkoo.memberservice.dto.request.RequestUpdatePasswordDto;
import com.ssafy.bookkoo.memberservice.dto.response.ResponseMemberInfoDto;
import com.ssafy.bookkoo.memberservice.dto.response.ResponseRecipientDto;
import com.ssafy.bookkoo.memberservice.service.MemberInfoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members/info")
public class MemberInfoController {

    private final MemberInfoService memberInfoService;

    @GetMapping
    @Operation(summary = "멤버 정보 반환 API", description = "멤버 ID(UUID)를 통해 멤버 정보를 반환합니다.")
    public ResponseEntity<ResponseMemberInfoDto> getMemberInfo(
        @RequestParam(name = "memberId") String memberId
    ) {
        ResponseMemberInfoDto memberInfo = memberInfoService.getMemberInfo(memberId);
        return ResponseEntity.ok(memberInfo);
    }

    @PostMapping("/password")
    @Operation(summary = "비밀번호 변경 API", description = "비밀번호를 변경합니다.")
    public ResponseEntity<HttpStatus> updatePassword(
        @Valid @RequestBody RequestUpdatePasswordDto requestUpdatePasswordDto
    ) {
        memberInfoService.updatePassword(requestUpdatePasswordDto);
        return ResponseEntity.ok()
                             .build();
    }

    @GetMapping("/{memberId}")
    @Operation(summary = "서비스간 통신에 필요한 Member PK(Long)반환 API",
        description = "서비스 내부에서 사용하기 위해 게이트웨이에서 사용하는 API입니다.")
    public ResponseEntity<Long> getMemberPK(
        @PathVariable("memberId") String memberId
    ) {
        Long id = memberInfoService.getMemberPk(memberId);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/recipients")
    @Operation(summary = "큐레이션 레터의 수신자들 반환 API",
        description = "큐레이션 레터의 수신자들을 반환하는 API입니다.")
    public ResponseEntity<List<ResponseRecipientDto>> getLetterRecipients() {
        //TODO: 팔로우 구현 후 팔로워 목록 + 랜덤 3명 ID반환 로직 구현
        return ResponseEntity.ok().build();
    }
}
