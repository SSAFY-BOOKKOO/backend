package com.ssafy.bookkoo.bookservice.repository.book;

import com.ssafy.bookkoo.bookservice.dto.book.RequestSearchBookMultiFieldDto;
import com.ssafy.bookkoo.bookservice.dto.book.RequestSearchBooksFilterDto;
import com.ssafy.bookkoo.bookservice.entity.Book;
import java.util.List;

public interface BookCustomRepository {

    List<Book> findByConditions(String type, String content, int offset, int limit);

    List<Book> findByConditions(RequestSearchBooksFilterDto dto);

    Book findByIsbn(String isbn);

    List<Book> findByConditions(RequestSearchBookMultiFieldDto filterDto);
}
