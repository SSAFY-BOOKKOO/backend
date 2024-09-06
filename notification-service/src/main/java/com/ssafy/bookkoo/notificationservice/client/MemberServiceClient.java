package com.ssafy.bookkoo.notificationservice.client;

import com.ssafy.bookkoo.notificationservice.client.dto.ResponseMemberInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "member-service")
public interface MemberServiceClient {
    String PASSPORT_HEADER = "member-passport";

    @GetMapping("/members/info")
    ResponseMemberInfoDto getMemberInfo(@RequestHeader(PASSPORT_HEADER) Long id);
}
