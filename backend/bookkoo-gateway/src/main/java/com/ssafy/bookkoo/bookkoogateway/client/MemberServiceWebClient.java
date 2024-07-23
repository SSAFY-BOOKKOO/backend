package com.ssafy.bookkoo.bookkoogateway.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberServiceWebClient {

    private final WebClient.Builder webClientBuilder;

    public Mono<Long> getMemberId(String memberId) {
        return webClientBuilder.build()
                               .get()
                               //TODO:member-service로 요청을 못보내는데 이를 해결할 방법을 알아봐야함.
                               .uri("http://localhost:8081/members/info/{memberId}", memberId)
                               .retrieve()
                               .bodyToMono(Long.class);
    }
}
