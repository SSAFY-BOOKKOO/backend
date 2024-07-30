package com.ssafy.bookkoo.libraryservice.client;

import com.ssafy.bookkoo.libraryservice.exception.MemberNotFoundException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "member-service")
public interface MemberServiceClient {

    String prefix = "/members";

    /**
     * nickname으로 memberId 받아오는 메서드 : 해당 사람의 서재 목록 찾을 때 사용됨
     *
     * @param nickname : nickname
     * @return memberId
     * @throws MemberNotFoundException(404)
     */
    @GetMapping(prefix + "/info/name")
    Long getMemberIdByNickName(@RequestParam("nickName") String nickname);
}
