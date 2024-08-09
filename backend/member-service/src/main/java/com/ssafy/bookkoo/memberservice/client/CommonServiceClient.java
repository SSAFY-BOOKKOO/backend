package com.ssafy.bookkoo.memberservice.client;

import com.ssafy.bookkoo.memberservice.client.dto.request.RequestSendEmailDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "common-service")
public interface CommonServiceClient {

    @PostMapping(value = "/commons/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String saveImg(
        @RequestPart(name = "file") MultipartFile file,
        @RequestPart(name = "bucket") String bucket);

    @DeleteMapping(value = "/commons/file")
    void deleteImg(
        @RequestParam(name = "file") String file,
        @RequestParam(name = "bucket") String bucket);

    @PostMapping(value = "/commons/email")
    void sendMail(@RequestBody RequestSendEmailDto sendEmailDto);
}
