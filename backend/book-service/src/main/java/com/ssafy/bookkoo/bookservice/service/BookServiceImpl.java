package com.ssafy.bookkoo.bookservice.service;

import com.ssafy.bookkoo.bookservice.dto.RequestCreateBookDto;
import com.ssafy.bookkoo.bookservice.dto.ResponseBookDto;
import com.ssafy.bookkoo.bookservice.entity.Book;
import com.ssafy.bookkoo.bookservice.exception.BookNotFoundException;
import com.ssafy.bookkoo.bookservice.repository.BookRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    /**
     * 책 생성
     *
     * @param bookDto : 책 생성 데이터
     * @return 생성한 책
     */
    @Override
    @Transactional
    public ResponseBookDto createBook(RequestCreateBookDto bookDto) {
        // DTO를 엔티티로 변환
        Book book = bookDto.toBook();

        // 책 저장
        Book savedBook = bookRepository.save(book);

        // 엔티티를 DTO로 변환
        return ResponseBookDto.builder()
                              .id(savedBook.getId())
                              .coverImgUrl(savedBook.getCoverImgUrl())
                              .author(savedBook.getAuthor())
                              .publisher(savedBook.getPublisher())
                              .summary(savedBook.getSummary())
                              .title(savedBook.getTitle())
                              .build();
    }

    /**
     * 책 검색(검색 종류, 검색 내용, 페이지, 페이지 내 개수)
     *
     * @param type    : 검색 종류
     * @param content : 검색 내용
     * @param offset  : 페이지
     * @param limit   : 페이지 내 개수
     * @return 검색결과 : List<ResponseBookDto>
     */
    @Override
    @Transactional
    public List<ResponseBookDto> getBooks(String type, String content, int offset, int limit) {
        List<Book> books = bookRepository.findByConditions(type, content, offset, limit);

        return books.stream()
                    // 더 효율적인 방법 있는지 생각해봐야지
                    .map(book -> ResponseBookDto.builder()
                                                .id(book.getId())
                                                .coverImgUrl(book.getCoverImgUrl())
                                                .author(book.getAuthor())
                                                .publisher(book.getPublisher())
                                                .summary(book.getSummary())
                                                .title(book.getTitle())
                                                .build())
                    .collect(Collectors.toList());
    }

    /**
     * 책 단일 조회 API
     *
     * @param bookId : 조회할 책 ID
     * @return 책 데이터(ResponseBookDto)
     */
    @Override
    @Transactional
    public ResponseBookDto getBook(Long bookId) {
        Book book = findBookByIdWithException(bookId);

        return ResponseBookDto.builder()
                              .id(book.getId())
                              .coverImgUrl(book.getCoverImgUrl())
                              .author(book.getAuthor())
                              .publisher(book.getPublisher())
                              .summary(book.getSummary())
                              .title(book.getTitle())
                              .build();
    }

    /**
     * 책 삭제 API
     *
     * @param bookId : 삭제할 책 ID
     * @return 삭제한 책 데이터(ResponseBookDto)
     */
    @Override
    @Transactional
    public ResponseBookDto deleteBook(Long bookId) {
        Book book = findBookByIdWithException(bookId);

        bookRepository.delete(book);
        return ResponseBookDto.builder()
                              .id(book.getId())
                              .coverImgUrl(book.getCoverImgUrl())
                              .author(book.getAuthor())
                              .publisher(book.getPublisher())
                              .summary(book.getSummary())
                              .title(book.getTitle())
                              .build();
    }

    private Book findBookByIdWithException(Long bookId) {
        return bookRepository.findById(bookId)
                             .orElseThrow(() -> new BookNotFoundException(
                                 "Book not found with id : " + bookId));
    }
}
