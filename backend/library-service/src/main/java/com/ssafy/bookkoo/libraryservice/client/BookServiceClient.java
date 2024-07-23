package com.ssafy.bookkoo.libraryservice.client;

import com.ssafy.bookkoo.libraryservice.dto.RequestBookDto;
import com.ssafy.bookkoo.libraryservice.dto.ResponseBookDto;
import com.ssafy.bookkoo.libraryservice.dto.ResponseCheckBooksByIsbnDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "book-service", url = "http://127.0.0.1:8002/books")
public interface BookServiceClient {

    @PostMapping
    ResponseBookDto addBook(@RequestBody RequestBookDto requestBookDto);

    @GetMapping("/{bookId}")
    ResponseBookDto getBookById(@PathVariable("bookId") String bookId);

    @GetMapping("/isbn/{isbn}")
    ResponseBookDto getBookByIsbn(@PathVariable("isbn") String isbn);

    @PostMapping("/check-books")
    List<ResponseCheckBooksByIsbnDto> checkBooksByIsbn(@RequestBody String[] isbnList);
}
