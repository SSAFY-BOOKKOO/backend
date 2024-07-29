package com.ssafy.bookkoo.curationservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.bookkoo.curationservice.dto.RequestChatbotDto;
import com.ssafy.bookkoo.curationservice.entity.Role;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


/**
 * 챗봇을 위한 OpenAPI 요청을 하는 서비스
 */
@Service
@RequiredArgsConstructor
public class OpenAiServiceImpl implements OpenAiService {

    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";
    private final RestTemplate restTemplate;
    private final String openAiApiKey;
    private final ObjectMapper objectMapper;


    /**
     * @param prompt : 사용자와 챗봇의 대화 리스트
     * @return API 응답으로 받은 챗봇의 대답
     */
    @Override
    public String getCompletion(ArrayList<RequestChatbotDto> prompt) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + openAiApiKey);
        headers.set("Content-Type", "application/json");
        ArrayList<RequestChatbotDto> promptList = new ArrayList<>();
        // 챗봇의 역할을 지정해주는 System 프롬프트
        //TODO 사용자 정보(성별, 나이, 선호 카테고리, 최근에 읽은 책) 을 넣는 부분 추가
        promptList.add(
            new RequestChatbotDto(Role.system,
                "너는 도서 서비스 챗봇이야 내가 주는 사용자의 입력을 받아서 처리해주면돼\n 사용자가 책 추천을 요구하면 너는 300자 이내로 큐레이션 레터를 만들어서 사용자에게 보여주면 돼 \n"));
        promptList.addAll(prompt);
        Map<String, Object> requestBody = new HashMap<>();
        // 사용할 모델 정보
        requestBody.put("model", "gpt-4o-mini");
        // 응답으로 받을 최대 토큰 수
        requestBody.put("max_tokens", 400);
        requestBody.put("messages", promptList);
        try {
            String requestJson = objectMapper.writeValueAsString(requestBody);
            HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
            ResponseEntity<String> response = restTemplate.exchange(OPENAI_API_URL, HttpMethod.POST,
                entity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return extractMessageField(response.getBody());
            } else {
                //TODO Exception 추가
                throw new RuntimeException("Failed to get response from OpenAI API");
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 응답 Json 을 파싱해여 필요한 부분만 반환하는 메서드
     *
     * @param responseBody : API 응답으로 받은 모든 내용
     * @return message 부분
     */
    private String extractMessageField(String responseBody) {
        try {
            JsonNode root = objectMapper.readTree(responseBody);
            JsonNode messageNode = root.path("choices")
                                       .get(0)
                                       .path("message");
            return objectMapper.writeValueAsString(messageNode);
        } catch (Exception e) {
            //TODO Exception 추가
            throw new RuntimeException("Failed to parse response body", e);
        }
    }


}
