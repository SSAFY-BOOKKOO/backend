package com.ssafy.bookkoo.commonservice.s3.controller;

import com.ssafy.bookkoo.commonservice.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/commons/file")
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping
    public ResponseEntity<HttpStatus> uploadFile(
        @RequestPart("file") MultipartFile file,
        @RequestPart(value = "bucket", required = false) String bucket
    ) {
        s3Service.saveToBucket(file, bucket);
        return ResponseEntity.ok()
                             .build();
    }
}
