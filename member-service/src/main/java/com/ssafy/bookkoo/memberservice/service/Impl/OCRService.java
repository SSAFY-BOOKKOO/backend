package com.ssafy.bookkoo.memberservice.service.Impl;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.protobuf.ByteString;
import com.ssafy.bookkoo.memberservice.exception.FileNotExistException;
import com.ssafy.bookkoo.memberservice.exception.OCRProcessingException;
import com.ssafy.bookkoo.memberservice.exception.TextNotDetectedException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class OCRService {

    public String extractTextFromImage(MultipartFile file) {
        if (file.isEmpty()) {
            throw new FileNotExistException();
        }

        try {
            // MultipartFile 이미지 -> ByteString으로 변환
            ByteString imgBytes = ByteString.readFrom(file.getInputStream());

            // ByteString을 통해 clude.vision의 image 객체 생성
            Image image = Image.newBuilder()
                               .setContent(imgBytes)
                               .build();

            // OCR 설정
            Feature feat = Feature.newBuilder()
                                  .setType(Feature.Type.TEXT_DETECTION)
                                  .build();

            // AnnotateImageRequest 객체 생성
            AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                                                               .addFeatures(feat)
                                                               .setImage(image)
                                                               .build();

            //요청 클라이언트를 생성하고 요청을 보낸 후 이에 대한 응답을 받는다.
            try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
                AnnotateImageResponse response = client.batchAnnotateImages(
                                                           Collections.singletonList(request))
                                                       .getResponses(0);

                // 에러 체크
                if (response.hasError()) {
                    log.error("Image Processing Error : {}", response.getError()
                                                                     .getMessage());
                    throw new OCRProcessingException();
                }

                //추출 결과
                List<EntityAnnotation> textAnnotationsList = response.getTextAnnotationsList();
                if (textAnnotationsList.isEmpty()) {
                    throw new TextNotDetectedException();
                }
                //OCR 결과 텍스트
                return response.getTextAnnotationsList()
                               .get(0)
                               .getDescription();
            }
        } catch (IOException e) {
            log.error("Failed to process the file : {}", e.getMessage());
            throw new OCRProcessingException();
        }
    }
}
