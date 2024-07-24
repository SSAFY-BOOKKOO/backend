package com.ssafy.bookkoo.bookkoogateway.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberServiceWebClient {

    private final WebClient.Builder webClientBuilder;
    private final String BASE_URL = "http://localhost:8081/members";

    public Mono<Long> getMemberId(String memberId) {
        return webClientBuilder.build()
                               .get()
                               .uri(BASE_URL + "/info/{memberId}", memberId)
                               .retrieve()
                               .bodyToMono(Long.class);
    }
}