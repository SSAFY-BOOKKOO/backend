package com.ssafy.bookkoo.bookservice.service;

import com.ssafy.bookkoo.bookservice.dto.RequestCreateBookDto;
import com.ssafy.bookkoo.bookservice.dto.ResponseBookDto;
import com.ssafy.bookkoo.bookservice.util.AladinAPI.AladinAPISearchParams;
import com.ssafy.bookkoo.bookservice.util.AladinAPI.ResponseAladinAPI;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import org.json.simple.parser.ParseException;

public interface BookService {

    @Transactional
    ResponseBookDto createBook(RequestCreateBookDto bookDto);

    @Transactional
    List<ResponseBookDto> getBooks(String type, String content, int offset, int limit);

    @Transactional
    ResponseBookDto getBook(Long bookId);

    @Transactional
    ResponseBookDto deleteBook(Long bookId);

    ResponseAladinAPI searchBooksFromAladin(AladinAPISearchParams params)
        throws IOException, InterruptedException, URISyntaxException, ParseException;
}
