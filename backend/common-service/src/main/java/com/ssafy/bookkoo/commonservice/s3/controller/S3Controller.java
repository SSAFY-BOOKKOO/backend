package com.ssafy.bookkoo.commonservice.s3.controller;

import com.ssafy.bookkoo.commonservice.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/commons/file")
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping
    public ResponseEntity<HttpStatus> uploadFile(
        @RequestHeader HttpHeaders headers,
        @RequestPart("file") MultipartFile file,
        @RequestPart(value = "bucket", required = false) String bucket
    ) {
        for (String s : headers.keySet()) {
            log.info("headers[{}] : {}", s, headers.get(s));

        }
        s3Service.saveToBucket(file, bucket);
        return ResponseEntity.ok()
                             .build();
    }
}
