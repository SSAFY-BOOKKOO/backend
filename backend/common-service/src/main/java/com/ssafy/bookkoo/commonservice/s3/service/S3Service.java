package com.ssafy.bookkoo.commonservice.s3.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface S3Service {

    String saveToBucket(MultipartFile file, String bucket);

    void deleteToBucket(String file, String bucket);

}
