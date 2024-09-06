package com.ssafy.bookkoo.bookkoogateway.client;

import com.ssafy.bookkoo.bookkoogateway.dto.ResponseTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthServiceWebClient {

    private final WebClient.Builder webClientBuilder;
    @Value("${config.base-url.auth}")
    private String BASE_URL;

    @Value("${jwt.refreshToken}")
    private String REFRESH_TOKEN;

    public Mono<ResponseTokenDto> getToken(String refreshToken) {
        return webClientBuilder.build()
                               .post()
                               .uri(BASE_URL + "/token")
                               .cookie(REFRESH_TOKEN, refreshToken)
                               .retrieve()
                               .bodyToMono(ResponseTokenDto.class);
    }
}
