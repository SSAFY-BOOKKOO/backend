package com.ssafy.bookkoo.commonservice.file.service.Impl;


import com.amazonaws.util.IOUtils;
import com.ssafy.bookkoo.commonservice.file.exception.FileDeleteFailException;
import com.ssafy.bookkoo.commonservice.file.exception.FileNotExistsException;
import com.ssafy.bookkoo.commonservice.file.exception.FileSaveFailException;
import com.ssafy.bookkoo.commonservice.file.service.FileService;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Primary
@Service
@RequiredArgsConstructor
public class MinioService implements FileService {

    private final MinioClient minioClient;

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
        try {
            minioClient.putObject(makePutObjectArgs(file, bucket, fileName));
        } catch (Exception e) {
            log.info("File Save Fail : {}", e.getMessage());
            throw new FileSaveFailException();
        }
        StringBuilder path = new StringBuilder();
        path.append("/");
        path.append(bucket);
        path.append("/");
        path.append(fileName);
        return path.toString();
    }

    private static PutObjectArgs makePutObjectArgs(MultipartFile file,
        String bucket, StringBuilder fileName) throws IOException {
        return PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(fileName.toString())
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build();
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
            minioClient.removeObject(makeRemoveObjectArgs(file, bucket));
        } catch (Exception e) {
            log.info("File Delete Fail : {}", e.getMessage());
            throw new FileDeleteFailException();
        }
    }

    private static RemoveObjectArgs makeRemoveObjectArgs(String file, String bucket) {
        return RemoveObjectArgs.builder()
                               .bucket(bucket)
                               .object(file)
                               .build();
    }

    /**
     * S3버킷으로부터 파일을 받아서 바이트로 바꿔서 반환합니다.
     * @param bucket
     * @param fileName
     * @return
     */
    @Override
    public byte[] getFile(String bucket, String fileName) {
        byte[] byteArrayFile = null;
        try {
            InputStream object = minioClient.getObject(makeGetObjectArgs(bucket, fileName));
            byteArrayFile = IOUtils.toByteArray(object);
        } catch (Exception e) {
            log.info("Get File Fail : {}", e.getMessage());
            throw new FileNotExistsException(fileName);
        }
        return byteArrayFile;
    }

    private static GetObjectArgs makeGetObjectArgs(String bucket, String fileName) {
        return GetObjectArgs.builder()
                            .bucket(bucket)
                            .object(fileName)
                            .build();
    }
}
