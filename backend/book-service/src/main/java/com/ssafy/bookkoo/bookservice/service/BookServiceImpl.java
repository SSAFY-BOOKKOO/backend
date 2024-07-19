package com.ssafy.bookkoo.bookservice.service;

import com.ssafy.bookkoo.bookservice.dto.RequestCreateBookDto;
import com.ssafy.bookkoo.bookservice.dto.ResponseBookDto;
import com.ssafy.bookkoo.bookservice.entity.Book;
import com.ssafy.bookkoo.bookservice.exception.BookNotFoundException;
import com.ssafy.bookkoo.bookservice.mapper.BookMapper;
import com.ssafy.bookkoo.bookservice.repository.BookRepository;
import com.ssafy.bookkoo.bookservice.util.AladinAPI.AladinAPIHandler;
import com.ssafy.bookkoo.bookservice.util.AladinAPI.AladinAPISearchParams;
import com.ssafy.bookkoo.bookservice.util.AladinAPI.ResponseAladinAPI;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AladinAPIHandler aladinAPIHandler;
    private final BookMapper bookMapper = BookMapper.INSTANCE;

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
        Book book = bookMapper.toEntity(bookDto);

        // 책 저장
        Book savedBook = bookRepository.save(book);

        // 엔티티를 DTO로 변환
        return bookMapper.toResponseDto(savedBook);
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

        return bookMapper.toResponseDtoList(books);
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

        return bookMapper.toResponseDto(book);
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
        return bookMapper.toResponseDto(book);
    }

    @Override
    public ResponseAladinAPI searchBooksFromAladin(AladinAPISearchParams params)
        throws IOException, InterruptedException, URISyntaxException, ParseException {
        return aladinAPIHandler.searchBooksFromAladin(params);
    }

    private Book findBookByIdWithException(Long bookId) {
        return bookRepository.findById(bookId)
                             .orElseThrow(() -> new BookNotFoundException(
                                 "Book not found with id : " + bookId));
    }
}
