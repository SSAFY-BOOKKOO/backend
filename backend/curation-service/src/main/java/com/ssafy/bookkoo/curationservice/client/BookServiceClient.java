package com.ssafy.bookkoo.curationservice.client;

import com.ssafy.bookkoo.curationservice.dto.CategoryDto;
import com.ssafy.bookkoo.curationservice.dto.CategorySearchParam;
import com.ssafy.bookkoo.curationservice.dto.ResponseBookDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "book-service")
public interface BookServiceClient {

    @GetMapping("/books/{bookId}")
    ResponseBookDto getBook(@PathVariable("bookId") Long bookId);

    @PostMapping("/categories/search")
    List<CategoryDto> getAllCategories(
        @RequestBody(required = false) CategorySearchParam params);

}
