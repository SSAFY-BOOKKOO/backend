package com.ssafy.bookkoo.commonservice.file.service.Impl;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.ssafy.bookkoo.commonservice.file.exception.FileDeleteFailException;
import com.ssafy.bookkoo.commonservice.file.exception.FileSaveFailException;
import com.ssafy.bookkoo.commonservice.file.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service implements FileService {

    private final AmazonS3Client amazonS3Client;

    /**
     * 버킷에 파일을 저장합니다. UUID를 추가하여 저장하고 해당 파일명을 반환합니다.
     *
     * @param file
     * @param bucket
     * @return
     */
    @Override
    public String saveToBucket(MultipartFile file, String bucket) {
        StringBuilder fileName = new StringBuilder();
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null) {
            fileName.append(UUID.randomUUID());
            fileName.append("_");
            fileName.append(UUID.randomUUID());
            fileName.append(".");
            fileName.append(StringUtils.getFilenameExtension(file.getOriginalFilename()));
        } else {
            throw new FileSaveFailException();
        }
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        try {
            amazonS3Client.putObject(bucket, fileName.toString(),
                file.getInputStream(), metadata);
        } catch (Exception e) {
            throw new FileSaveFailException();
        }
        StringBuilder path = new StringBuilder();
        path.append("/");
        path.append(bucket);
        path.append("/");
        path.append(fileName);
        return path.toString();
    }

    /**
     * 버킷에서 파일을 삭제합니다.
     *
     * @param file
     * @param bucket
     */
    @Override
    public void deleteToBucket(String file, String bucket) {
        try {
            amazonS3Client.deleteObject(bucket, file);
        } catch (Exception e) {
            throw new FileDeleteFailException();
        }
    }

    /**
     * S3버킷으로부터 파일을 받아서 바이트로 바꿔서 반환합니다.
     * @param bucket
     * @param fileName
     * @return
     */
    @Override
    public byte[] getFile(String bucket, String fileName) {
        S3Object object = amazonS3Client.getObject(bucket, fileName);
        byte[] byteArrayFile = null;
        try {
             byteArrayFile = IOUtils.toByteArray(object.getObjectContent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return byteArrayFile;
    }
}
