package com.ssafy.bookkoo.bookservice.service;

import com.ssafy.bookkoo.bookservice.dto.RequestCreateBookDto;
import com.ssafy.bookkoo.bookservice.dto.ResponseBookDto;
import jakarta.transaction.Transactional;
import java.util.List;

public interface BookService {

    @Transactional
    ResponseBookDto createBook(RequestCreateBookDto bookDto);

    @Transactional
    List<ResponseBookDto> getBooks(String type, String content, int offset, int limit);

    @Transactional
    ResponseBookDto getBook(Long bookId);

    @Transactional
    ResponseBookDto deleteBook(Long bookId);
}
