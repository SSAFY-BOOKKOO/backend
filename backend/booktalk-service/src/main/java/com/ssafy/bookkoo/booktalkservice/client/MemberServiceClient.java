package com.ssafy.bookkoo.booktalkservice.client;

import com.ssafy.bookkoo.booktalkservice.dto.ResponseMemberInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "member-service")
public interface MemberServiceClient {

    @GetMapping("/members/info/id/{memberId}")
    ResponseMemberInfoDto getMemberInfoById(
        @PathVariable("memberId") Long memberId
    );
}

