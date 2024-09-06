package com.ssafy.bookkoo.commonservice.file.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileService {

    String saveToBucket(MultipartFile file, String bucket);

    void deleteToBucket(String file, String bucket);

    byte[] getFile(String bucket, String fileName);
}
