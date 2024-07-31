package com.ssafy.bookkoo.bookservice.client;

import com.ssafy.bookkoo.bookservice.dto.other.ResponseMemberInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "member-service")
public interface MemberServiceClient {

    String BASE_URL = "/members";
    String BASE_INFO_URL = BASE_URL + "/info";

    @GetMapping(BASE_INFO_URL + "/id/{memberId}")
    ResponseMemberInfoDto getMemberInfoById(@PathVariable("memberId") Long memberId);
}
