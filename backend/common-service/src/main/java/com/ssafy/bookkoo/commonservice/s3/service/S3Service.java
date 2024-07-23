package com.ssafy.bookkoo.commonservice.s3.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface S3Service {

    void saveToBucket(MultipartFile file, String bucket);
}
