package com.ssafy.bookkoo.libraryservice.client;

import com.ssafy.bookkoo.libraryservice.dto.RequestBookDto;
import com.ssafy.bookkoo.libraryservice.dto.ResponseBookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class BookServiceClient {

    private final WebClient.Builder webClientBuilder;
    private final String BASE_URL = "http://127.0.0.1:8000/books";

    public ResponseBookDto addBook(RequestBookDto bookDto) {
        return webClientBuilder.build()
                               .post()
                               .uri(BASE_URL)
                               .bodyValue(bookDto)
                               .retrieve()
                               .bodyToMono(ResponseBookDto.class)
                               .block();
    }

    public ResponseBookDto getBook(Long bookId) {
        return webClientBuilder.build()
                               .get()
                               .uri(BASE_URL + "/{bookId}", bookId)
                               .retrieve()
                               .bodyToMono(ResponseBookDto.class)
                               .block();
    }
}
