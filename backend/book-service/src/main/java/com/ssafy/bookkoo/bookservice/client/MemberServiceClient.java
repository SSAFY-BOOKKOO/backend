package com.ssafy.bookkoo.bookservice.client;

import com.ssafy.bookkoo.bookservice.dto.other.ResponseMemberInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * OpenFeign 을 이용하여 member service 와 통신
 */
@FeignClient(name = "member-service")
public interface MemberServiceClient {

    // Member 기본 URL
    String BASE_URL = "/members";
    // MemberInfo 기본 URL
    String BASE_INFO_URL = BASE_URL + "/info";

    /**
     * memberId 로 MemberInfo 받아오는 API
     *
     * @param memberId : member PK
     * @return : ResponseMemberInfoDto
     */
    @GetMapping(BASE_INFO_URL + "/id/{memberId}")
    ResponseMemberInfoDto getMemberInfoById(@PathVariable("memberId") Long memberId);
}
