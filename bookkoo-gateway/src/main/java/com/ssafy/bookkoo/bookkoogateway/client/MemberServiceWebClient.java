package com.ssafy.bookkoo.bookkoogateway.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MemberServiceWebClient {

    private final WebClient.Builder webClientBuilder;
    @Value("${config.base-url.member}")
    private String BASE_URL;

    public Mono<Long> getMemberId(String memberId) {
        return webClientBuilder.build()
                               .get()
                               .uri(BASE_URL + "/info/{memberId}", memberId)
                               .retrieve()
                               .bodyToMono(Long.class);
    }
}
