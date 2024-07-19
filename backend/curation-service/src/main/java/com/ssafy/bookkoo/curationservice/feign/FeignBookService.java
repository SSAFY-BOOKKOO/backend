package com.ssafy.bookkoo.curationservice.feign;

import com.ssafy.bookkoo.curationservice.dto.ResponseBookDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "book-service")
public interface FeignBookService {

    @GetMapping("/books/{bookId}")
    ResponseBookDto getBook(@PathVariable("bookId") Long bookId);

}
