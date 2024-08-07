package com.ssafy.bookkoo.booktalkservice.client;

import com.ssafy.bookkoo.booktalkservice.dto.ResponseBookDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "book-service")
public interface BookServiceClient {

    @GetMapping("/books/{bookId}")
    ResponseBookDto getBook(@PathVariable("bookId") Long bookId);
}
