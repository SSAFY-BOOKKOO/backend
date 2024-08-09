package com.ssafy.bookkoo.memberservice.service.Impl;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.protobuf.ByteString;
import com.ssafy.bookkoo.memberservice.exception.FileNotExistException;
import com.ssafy.bookkoo.memberservice.exception.OCRProcessingException;
import com.ssafy.bookkoo.memberservice.exception.TextNotDetectedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class OCRService {

    public String extractTextFromImage(MultipartFile file) {
        if (file.isEmpty()) {
            throw new FileNotExistException();
        }

        try {
            // Convert the MultipartFile to ByteString
            ByteString imgBytes = ByteString.readFrom(file.getInputStream());

            // Correctly build the Image object from ByteString
            Image image = Image.newBuilder().setContent(imgBytes).build();

            // Set the feature to detect text (OCR)
            Feature feat = Feature.newBuilder()
                                  .setType(Feature.Type.TEXT_DETECTION)
                                  .build();

            // Build the AnnotateImageRequest object
            AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                                                               .addFeatures(feat)
                                                               .setImage(image)
                                                               .build();

            // Create the ImageAnnotatorClient and perform the OCR request
            try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
                AnnotateImageResponse response = client.batchAnnotateImages(
                                                           Collections.singletonList(request))
                                                       .getResponses(0);

                // Check if there is any error in the response
                if (response.hasError()) {
                    log.error("Error during image processing: {}", response.getError()
                                                                           .getMessage());
                    throw new OCRProcessingException();
                }

                // Extract and return the detected text
                String text = response.getTextAnnotationsList().isEmpty() ?
                    "" :
                    response.getTextAnnotationsList().get(0).getDescription();

                if (text.isEmpty()) {
                    throw new TextNotDetectedException();
                }

                return text;
            }
        } catch (IOException e) {
            log.error("Failed to process the file", e);
            throw new OCRProcessingException();
        }
    }
}
