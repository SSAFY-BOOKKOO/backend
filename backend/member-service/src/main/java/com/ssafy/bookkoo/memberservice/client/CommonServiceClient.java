package com.ssafy.bookkoo.memberservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "common-service")
public interface CommonServiceClient {

    @PostMapping(value = "/commons/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String saveProfileImg(
        @RequestPart(name = "file") MultipartFile profileImg,
        @RequestPart(name = "buekct", required = false) String bucket);
}
