package com.ssafy.bookkoo.memberservice.controller;

import com.ssafy.bookkoo.memberservice.dto.request.RequestFollowShipDto;
import com.ssafy.bookkoo.memberservice.dto.response.ResponseFollowShipDto;
import com.ssafy.bookkoo.memberservice.service.FollowShipService;
import com.ssafy.bookkoo.memberservice.service.MemberInfoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members/follow")
public class FollowController {

    private static final Logger log = LoggerFactory.getLogger(FollowController.class);
    private final String PASSPORT_PREFIX = "member-passport";
    private final FollowShipService followShipService;
    private final MemberInfoService memberInfoService;

    @PostMapping("/follow")
    @Operation(summary = "팔로우 요청 API", description = "멤버가 팔로우 요청을 합니다.")
    public ResponseEntity<HttpStatus> follow(
        @RequestHeader HttpHeaders headers,
        @RequestBody RequestFollowShipDto requestFollowShipDto
    ) {
        Long followerId = Long.valueOf(headers.getFirst(PASSPORT_PREFIX));
        Long followeeId = memberInfoService.getMemberPk(requestFollowShipDto.memberId());
        log.info("{} followed {}", followerId, followeeId);
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
        Long followerId = Long.valueOf(headers.getFirst(PASSPORT_PREFIX));
        Long followeeId = memberInfoService.getMemberPk(requestFollowShipDto.memberId());
        followShipService.unFollow(followerId, followeeId);
        return ResponseEntity.ok()
                             .build();

    }

    @GetMapping("/followees")
    @Operation(summary = "팔로워 목록을 반환합니다.", description = "멤버가 팔로우 하는 목록을 반환합니다.")
    public ResponseEntity<List<ResponseFollowShipDto>> getFollowees(
        @RequestHeader HttpHeaders headers,
        @RequestParam(required = false) RequestFollowShipDto requestFollowShipDto
    ) {
        Long memberId = Long.valueOf(headers.getFirst(PASSPORT_PREFIX));
        List<ResponseFollowShipDto> followees = followShipService.getFollowees(memberId);
        return ResponseEntity.ok(followees);
    }

    @GetMapping("/followers")
    @Operation(summary = "팔로워 목록을 반환합니다.", description = "멤버 자신을 팔로우 하는 목록을 반환합니다.")
    public ResponseEntity<List<ResponseFollowShipDto>> getFollowers(
        @RequestHeader HttpHeaders headers,
        @RequestParam(required = false) RequestFollowShipDto requestFollowShipDto
    ) {
        Long memberId = Long.valueOf(headers.getFirst(PASSPORT_PREFIX));
        List<ResponseFollowShipDto> followers = followShipService.getFollowers(memberId);
        return ResponseEntity.ok(followers);
    }
}
