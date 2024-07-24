package com.ssafy.bookkoo.curationservice.client;

import com.ssafy.bookkoo.curationservice.dto.ResponseBookDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "book-service")
public interface BookServiceClient {

    @GetMapping("/books/{bookId}")
    ResponseBookDto getBook(@PathVariable("bookId") Long bookId);

}
