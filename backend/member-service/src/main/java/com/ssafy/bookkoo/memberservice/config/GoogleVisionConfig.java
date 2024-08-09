package com.ssafy.bookkoo.memberservice.config;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.ImageAnnotatorSettings;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class GoogleVisionConfig {

    @Value("${google.cloud.vision.credentials}")
    private String credentialsString;

    @Bean
    public ImageAnnotatorClient createClient() throws IOException {
//        // Your actual base64 string
//        String base64EncodedKey = credentialsString;
//
//        // Decode base64 to JSON string
//        byte[] decodedKey = Base64.getDecoder().decode(base64EncodedKey);
//        String decodedJson = new String(decodedKey, "UTF-8");
//
//        // Print decoded JSON to debug
//        System.out.println("Decoded JSON: " + decodedJson);

        InputStream credentialsStream = new ClassPathResource("bookkoo-ab8f9dae0c13.json").getInputStream();

        // Load the credentials
        GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream);
        // Create InputStream from decoded JSON
//        ByteArrayInputStream credentialsStream = new ByteArrayInputStream(decodedJson.getBytes());
//        GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream)
//                                                         .createScoped("https://www.googleapis.com/auth/cloud-platform");
        // Load credentials
        credentials = credentials.createWithQuotaProject("bookkoo");
        // Set up ImageAnnotatorClient
        GoogleCredentials finalCredentials = credentials;
        ImageAnnotatorSettings settings = ImageAnnotatorSettings.newBuilder()
                                                                .setCredentialsProvider(() -> finalCredentials)
                                                                .build();

        return ImageAnnotatorClient.create(settings);
    }
}
