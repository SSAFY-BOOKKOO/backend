package com.ssafy.bookkoo.curationservice.client;


import com.ssafy.bookkoo.curationservice.dto.ResponseMemberInfoDto;
import com.ssafy.bookkoo.curationservice.dto.ResponseRecipientDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "member-service")
public interface MemberServiceClient {

    @GetMapping("/members/info")
    ResponseMemberInfoDto getMemberInfo(
        @RequestParam(name = "memberId") String memberId
    );

    @GetMapping("/members/info/curation/recipients")
    List<ResponseRecipientDto> getLetterRecipients(
        @RequestParam("memberId") Long memberId
    );

    @GetMapping("/members/info/id/{memberId}")
    public ResponseMemberInfoDto getMemberInfoById(
        @PathVariable("memberId") Long memberId
    );
}
