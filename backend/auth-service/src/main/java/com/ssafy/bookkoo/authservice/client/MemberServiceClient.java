package com.ssafy.bookkoo.authservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "member-service")
public interface MemberServiceClient {

    @PostMapping(value = "/members/register/social")
    String register(@RequestBody String email);

    @GetMapping(value = "/members/info/{memberId}")
    Long getMemberPK(@PathVariable("memberId") String memberId);
}
