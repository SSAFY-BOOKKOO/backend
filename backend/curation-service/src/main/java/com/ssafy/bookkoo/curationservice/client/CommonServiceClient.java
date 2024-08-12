package com.ssafy.bookkoo.curationservice.client;

import com.ssafy.bookkoo.curationservice.dto.RequestSendEmailDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "common-service")
public interface CommonServiceClient {

    @PostMapping(value = "/commons/email")
    void sendMail(@RequestBody RequestSendEmailDto sendEmailDto);
}
