package com.ssafy.bookkoo.commonservice.s3.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    /**
     * 버킷에 파일을 저장합니다.
     * UUID를 추가하여 저장하고 해당 파일명을 반환합니다.
     * @param file
     * @param bucket
     * @return
     */
    @Override
    public String saveToBucket(MultipartFile file, String bucket) {
        StringBuilder fileName = new StringBuilder();
        fileName.append(file.getOriginalFilename());
        fileName.append("_");
        fileName.append(UUID.randomUUID());
        fileName.append(".");
        fileName.append(StringUtils.getFilenameExtension(file.getOriginalFilename()));
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        String instance = bucket == null ? bucketName : bucket;
        try {
            amazonS3Client.putObject(instance, fileName.toString(),
                file.getInputStream(), metadata);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileName.toString();
    }

    /**
     * 버킷에서 파일을 삭제합니다.
     * @param file
     * @param bucket
     */
    @Override
    public void deleteToBucket(String file, String bucket) {
        String instance = bucket == null ? bucketName : bucket;
        amazonS3Client.deleteObject(instance, file);
    }
}
