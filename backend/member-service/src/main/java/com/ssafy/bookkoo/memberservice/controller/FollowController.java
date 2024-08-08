package com.ssafy.bookkoo.memberservice.controller;

import com.ssafy.bookkoo.memberservice.dto.request.RequestFollowShipDto;
import com.ssafy.bookkoo.memberservice.dto.response.ResponseFollowShipDto;
import com.ssafy.bookkoo.memberservice.service.FollowShipService;
import com.ssafy.bookkoo.memberservice.service.MemberInfoService;
import com.ssafy.bookkoo.memberservice.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members/follow")
public class FollowController {

    private final FollowShipService followShipService;
    private final MemberInfoService memberInfoService;

    @PostMapping("/follow")
    @Operation(summary = "팔로우 요청 API", description = "멤버가 팔로우 요청을 합니다.")
    public ResponseEntity<HttpStatus> follow(
        @RequestHeader HttpHeaders headers,
        @RequestBody RequestFollowShipDto requestFollowShipDto
    ) {
        Long followerId = CommonUtil.getMemberId(headers);
        Long followeeId = memberInfoService.getMemberPk(requestFollowShipDto.memberId());
        followShipService.follow(followerId, followeeId);
        return ResponseEntity.ok()
                             .build();
    }

    @PostMapping("/unfollow")
    @Operation(summary = "언팔로우 요청 API", description = "멤버가 언팔로우 요청을 합니다.")
    public ResponseEntity<HttpStatus> unfollow(
        @RequestHeader HttpHeaders headers,
        @RequestBody RequestFollowShipDto requestFollowShipDto
    ) {
        Long followerId = CommonUtil.getMemberId(headers);
        Long followeeId = memberInfoService.getMemberPk(requestFollowShipDto.memberId());
        followShipService.unFollow(followerId, followeeId);
        return ResponseEntity.ok()
                             .build();

    }

    @GetMapping("/followees")
    @Operation(summary = "팔로잉 목록을 반환합니다.", description = "멤버가 팔로우 하는 목록을 반환합니다.")
    public ResponseEntity<List<ResponseFollowShipDto>> getFollowees(
        @RequestHeader HttpHeaders headers,
        @RequestParam(required = false) RequestFollowShipDto requestFollowShipDto
    ) {
        Long id = CommonUtil.getMemberId(headers);
        if (requestFollowShipDto != null) {
            id = memberInfoService.getMemberPk(requestFollowShipDto.memberId());
        }
        List<ResponseFollowShipDto> followees = followShipService.getFollowees(id);
        return ResponseEntity.ok(followees);
    }

    @GetMapping("/followers")
    @Operation(summary = "팔로워 목록을 반환합니다.", description = "멤버 자신을 팔로우 하는 목록을 반환합니다.")
    public ResponseEntity<List<ResponseFollowShipDto>> getFollowers(
        @RequestHeader HttpHeaders headers,
        @RequestParam(required = false) RequestFollowShipDto requestFollowShipDto
    ) {
        Long id = CommonUtil.getMemberId(headers);
        if (requestFollowShipDto != null) {
            id = memberInfoService.getMemberPk(requestFollowShipDto.memberId());
        }
        List<ResponseFollowShipDto> followers = followShipService.getFollowers(id);
        return ResponseEntity.ok(followers);
    }

    @DeleteMapping("/{memberId}")
    @Operation(summary = "나를 팔로우하는 상대를 팔로우 취소하게 만드는 API",
        description = "나를 팔로우 하는 대상이 더 이상 나를 팔로우 하지 않도록 하는 API")
    public ResponseEntity<HttpStatus> deleteMyFollower(
        @RequestHeader HttpHeaders headers,
        @PathVariable(value = "memberId") String memberId
    ) {
        Long followerId = memberInfoService.getMemberPk(memberId);
        Long followeeId = CommonUtil.getMemberId(headers);
        followShipService.unFollow(followerId, followeeId);
        return ResponseEntity.noContent()
                             .build();
    }
}
