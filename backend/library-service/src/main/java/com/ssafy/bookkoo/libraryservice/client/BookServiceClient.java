package com.ssafy.bookkoo.libraryservice.client;

import com.ssafy.bookkoo.libraryservice.dto.RequestCreateBookDto;
import com.ssafy.bookkoo.libraryservice.dto.ResponseBookDto;
import com.ssafy.bookkoo.libraryservice.dto.ResponseCheckBooksByIsbnDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "book-service")
public interface BookServiceClient {

    final String prefix = "/books";

    @PostMapping(prefix)
    ResponseBookDto addBook(@RequestBody RequestCreateBookDto requestBookDto);

    @GetMapping(prefix)
    List<ResponseBookDto> getBooksByCondition(
        @RequestParam("field") String field,
        @RequestParam("value") List<String> value,
        @RequestParam("limit") Integer limit,
        @RequestParam("offset") Integer offset
    );

    @GetMapping(prefix + "/{bookId}")
    ResponseBookDto getBookById(@PathVariable("bookId") String bookId);

    @GetMapping(prefix + "/isbn/{isbn}")
    ResponseBookDto getBookByIsbn(@PathVariable("isbn") String isbn);

    @PostMapping(prefix + "/isbn")
    ResponseBookDto getOrCreateBookByBookData(@RequestBody RequestCreateBookDto requestBookDto);

    @PostMapping(prefix + "/check-books")
    List<ResponseCheckBooksByIsbnDto> checkBooksByIsbn(@RequestBody String[] isbnList);
}
