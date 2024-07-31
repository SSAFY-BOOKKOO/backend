package com.ssafy.bookkoo.bookservice.service.book;

import com.ssafy.bookkoo.bookservice.dto.book.RequestCreateBookDto;
import com.ssafy.bookkoo.bookservice.dto.book.RequestSearchBookMultiFieldDto;
import com.ssafy.bookkoo.bookservice.dto.book.RequestSearchBooksFilterDto;
import com.ssafy.bookkoo.bookservice.dto.book.ResponseBookDto;
import com.ssafy.bookkoo.bookservice.dto.book.ResponseCheckBooksByIsbnDto;
import com.ssafy.bookkoo.bookservice.util.AladinAPI.AladinAPISearchParams;
import com.ssafy.bookkoo.bookservice.util.AladinAPI.ResponseAladinAPI;
import com.ssafy.bookkoo.bookservice.util.AladinAPI.ResponseAladinSearchDetail;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import org.json.simple.parser.ParseException;
import org.springframework.transaction.annotation.Transactional;

public interface BookService {

    @Transactional
    ResponseBookDto createBook(RequestCreateBookDto bookDto);

    @Transactional
    List<ResponseBookDto> getBooks(
        String type,
        String content,
        int offset,
        int limit
    );

    @Transactional
    ResponseBookDto getBook(Long bookId);

    @Transactional
    ResponseBookDto getBookByIsbn(String isbn);

    @Transactional
    ResponseBookDto deleteBook(Long bookId);

    ResponseAladinAPI searchBooksFromAladin(AladinAPISearchParams params)
        throws IOException, InterruptedException, URISyntaxException, ParseException;

    @Transactional
    List<ResponseCheckBooksByIsbnDto> checkBooksByIsbn(String[] isbnList);

    @Transactional
    ResponseBookDto getOrCreateBookByBookData(RequestCreateBookDto bookDto);

    @Transactional
    List<ResponseBookDto> getBooksByCondition(RequestSearchBooksFilterDto filterDto);

    ResponseAladinSearchDetail searchBookDetailFromAladin(String isbn)
        throws IOException, InterruptedException, URISyntaxException, ParseException;

    @Transactional(readOnly = true)
    List<ResponseBookDto> getBooksByCondition(RequestSearchBookMultiFieldDto filterDto);
}
