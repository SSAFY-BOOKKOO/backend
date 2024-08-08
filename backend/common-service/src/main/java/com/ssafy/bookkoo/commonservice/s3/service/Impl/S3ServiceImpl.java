package com.ssafy.bookkoo.commonservice.s3.service.Impl;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.ssafy.bookkoo.commonservice.s3.exception.FileDeleteFailException;
import com.ssafy.bookkoo.commonservice.s3.exception.FileSaveFailException;
import com.ssafy.bookkoo.commonservice.s3.service.S3Service;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    private final AmazonS3Client amazonS3Client;
    private final String PROTOCAL = "https://";
    private final String AWS_PATH = ".s3.ap-northeast-2.amazonaws.com/";

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
        fileName.append(file.getOriginalFilename());
        fileName.append("_");
        fileName.append(UUID.randomUUID());
        fileName.append(".");
        fileName.append(StringUtils.getFilenameExtension(file.getOriginalFilename()));
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        try {
            amazonS3Client.putObject(bucket, fileName.toString(),
                file.getInputStream(), metadata);
        } catch (Exception e) {
            throw new FileSaveFailException();
        }
        StringBuilder path = new StringBuilder(PROTOCAL);
        path.append(bucket);
        path.append(AWS_PATH);
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
}
