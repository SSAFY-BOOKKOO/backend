package com.ssafy.bookkoo.commonservice.file.controller;

import com.ssafy.bookkoo.commonservice.file.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/commons/file")
public class FileController {

    private final FileService fileService;

    @PostMapping
    public ResponseEntity<String> uploadFile(
        @RequestPart("file") MultipartFile file,
        @RequestPart("bucket") String bucket
    ) {
        String fileKey = fileService.saveToBucket(file, bucket);
        return ResponseEntity.ok(fileKey);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteFile(
        @RequestParam("file") String file,
        @RequestParam("bucket") String bucket
    ) {
        fileService.deleteToBucket(file, bucket);
        return ResponseEntity.ok()
                             .build();
    }

    @GetMapping("/{bucket}/{fileName}")
    public ResponseEntity<byte[]> getFileByBucket(
        @PathVariable(value = "bucket") String bucket,
        @PathVariable(value = "fileName") String fileName
    ) {
        HttpHeaders headers = new HttpHeaders();
        byte[] file = fileService.getFile(bucket, fileName);
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(file.length);
        return ResponseEntity.ok()
                             .headers(headers)
                             .body(file);
    }
}
