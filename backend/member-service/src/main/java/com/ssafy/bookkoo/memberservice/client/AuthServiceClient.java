package com.ssafy.bookkoo.memberservice.client;

import com.ssafy.bookkoo.memberservice.dto.request.RequestLoginDto;
import com.ssafy.bookkoo.memberservice.dto.response.ResponseLoginTokenDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//TODO: WebClient로 바꿔보기
@FeignClient(name = "auth-service")
public interface AuthServiceClient {

    @PostMapping(value = "/auth/login/email")
    ResponseLoginTokenDto login(
        @RequestBody RequestLoginDto requestLoginDto);
}
