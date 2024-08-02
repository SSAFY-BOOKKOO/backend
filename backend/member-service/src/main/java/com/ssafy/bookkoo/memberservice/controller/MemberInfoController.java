package com.ssafy.bookkoo.memberservice.controller;

import com.ssafy.bookkoo.memberservice.dto.request.RequestUpdatePasswordDto;
import com.ssafy.bookkoo.memberservice.dto.response.ResponseFollowShipDto;
import com.ssafy.bookkoo.memberservice.dto.response.ResponseMemberInfoDto;
import com.ssafy.bookkoo.memberservice.dto.response.ResponseMemberProfileDto;
import com.ssafy.bookkoo.memberservice.service.FollowShipService;
import com.ssafy.bookkoo.memberservice.service.MemberInfoService;
import com.ssafy.bookkoo.memberservice.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members/info")
public class MemberInfoController {

    private final MemberInfoService memberInfoService;
    private final FollowShipService followShipService;

    @GetMapping
    @Operation(summary = "멤버 정보 반환 API", description = "멤버 ID(UUID)를 통해 멤버 정보를 반환합니다. (멤버의 프로필 정보에 보여지는 데이터를 반환합니다.)")
    public ResponseEntity<ResponseMemberProfileDto> getMemberInfo(
        @RequestHeader HttpHeaders headers,
        @RequestParam(name = "memberId", required = false) String memberId
    ) {
        ResponseMemberProfileDto memberProfileDto = null;
        if (memberId == null) {
            Long id = CommonUtil.getMemberId(headers);
            memberProfileDto = memberInfoService.getMemberProfileInfo(id);
        } else {
            memberProfileDto = memberInfoService.getMemberProfileInfo(memberId);
        }
        return ResponseEntity.ok(memberProfileDto);
    }


    @GetMapping("/id/{memberId}")
    @Operation(summary = "멤버 PK를 통해 멤버 정보를 반환하는 API",
        description = "서비스 내부에서 사용하기 위해 게이트웨이에서 사용하는 API입니다.")
    public ResponseEntity<ResponseMemberInfoDto> getMemberInfoById(
        @PathVariable("memberId") Long memberId
    ) {
        ResponseMemberInfoDto memberInfo = memberInfoService.getMemberInfo(memberId);
        return ResponseEntity.ok(memberInfo);
    }

    @PatchMapping("/password")
    @Operation(summary = "비밀번호 변경 API", description = "비밀번호를 변경합니다.")
    public ResponseEntity<HttpStatus> updatePassword(
        @RequestHeader HttpHeaders headers,
        @Valid @RequestBody RequestUpdatePasswordDto requestUpdatePasswordDto
    ) {
        Long id = CommonUtil.getMemberId(headers);
        memberInfoService.updatePassword(id, requestUpdatePasswordDto);
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

    @GetMapping("/curation/recipients")
    @Operation(summary = "큐레이션 레터의 수신자들 반환 API",
        description = "큐레이션 레터의 수신자들을 반환하는 API입니다. (팔로워 + 랜덤 3명)")
    public ResponseEntity<List<Long>> getLetterRecipients(
        @RequestParam("memberId") Long memberId
    ) {
        List<ResponseFollowShipDto> followers = followShipService.getFollowers(memberId);
        List<Long> followerIds = new ArrayList<>(followers.stream()
                                                           .map(ResponseFollowShipDto::memberId)
                                                           .toList());

        //자기 자신 ID 추가 (follwerIds의 마지막에 추가)
        followerIds.add(memberId);
        List<Long> recipientIds = memberInfoService.getRandomMemberInfo(followerIds);
        recipientIds.addAll(followerIds);
        //마지막 원소 제거(자기 자신)
        recipientIds.remove(recipientIds.size() - 1);
        return ResponseEntity.ok()
                             .body(recipientIds);
    }

    @GetMapping("/name")
    public ResponseEntity<Long> getMemberIdByNickName(
        @RequestParam("nickName") String nickName
    ) {
        Long memberId = memberInfoService.getMemberIdByNickName(nickName);
        return ResponseEntity.ok(memberId);
    }
}
