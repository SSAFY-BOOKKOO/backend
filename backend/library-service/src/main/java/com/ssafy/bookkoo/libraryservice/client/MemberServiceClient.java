package com.ssafy.bookkoo.libraryservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "member-service")
public interface MemberServiceClient {

    String prefix = "/members";

    @GetMapping(prefix + "/name")
    Long getMemberIdByNickName(@RequestParam("nickname") String nickname);
}
