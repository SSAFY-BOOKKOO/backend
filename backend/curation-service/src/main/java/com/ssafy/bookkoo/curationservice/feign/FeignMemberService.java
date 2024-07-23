package com.ssafy.bookkoo.curationservice.feign;


import com.ssafy.bookkoo.curationservice.dto.ResponseMemberInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "member-service")
public interface FeignMemberService {

    @GetMapping("/members/info")
    ResponseMemberInfoDto getMemberInfo(@RequestParam(name = "memberId") String memberId);

}
