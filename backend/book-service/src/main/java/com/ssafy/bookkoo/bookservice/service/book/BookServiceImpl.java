package com.ssafy.bookkoo.bookservice.service.book;

import com.ssafy.bookkoo.bookservice.dto.RequestCreateBookDto;
import com.ssafy.bookkoo.bookservice.dto.RequestSearchBooksFilterDto;
import com.ssafy.bookkoo.bookservice.dto.ResponseBookDto;
import com.ssafy.bookkoo.bookservice.dto.ResponseCheckBooksByIsbnDto;
import com.ssafy.bookkoo.bookservice.entity.Book;
import com.ssafy.bookkoo.bookservice.entity.Category;
import com.ssafy.bookkoo.bookservice.exception.BookNotFoundException;
import com.ssafy.bookkoo.bookservice.exception.CategoryNotFoundException;
import com.ssafy.bookkoo.bookservice.mapper.BookMapper;
import com.ssafy.bookkoo.bookservice.repository.BookRepository;
import com.ssafy.bookkoo.bookservice.repository.CategoryRepository;
import com.ssafy.bookkoo.bookservice.util.AladinAPI.AladinAPIHandler;
import com.ssafy.bookkoo.bookservice.util.AladinAPI.AladinAPISearchParams;
import com.ssafy.bookkoo.bookservice.util.AladinAPI.ResponseAladinAPI;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
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

        Category category = categoryRepository.findById(bookDto.categoryId())
                                              .orElseThrow(
                                                  () -> new CategoryNotFoundException(
                                                      "category Not found")
                                              );

        book.setCategory(category);

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
    public List<ResponseBookDto> getBooks(
        String type,
        String content,
        int offset,
        int limit
    ) {
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
     * 책 단일 조회 API by isbn
     *
     * @param isbn : isbn
     * @return 책 데이터
     */
    @Override
    @Transactional
    public ResponseBookDto getBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn);

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
        throws IOException, InterruptedException, URISyntaxException {
        return aladinAPIHandler.searchBooksFromAladin(params);
    }

    /**
     * isbn 리스트를 받아서 각 isbn에 해당하는 book이 db에 있는지 확인 후 반환
     *
     * @param isbnList : isbn 리스트
     * @return List<ResponseCheckBooksByIsbnDto>
     */
    @Override
    public List<ResponseCheckBooksByIsbnDto> checkBooksByIsbn(String[] isbnList) {
        return Arrays.stream(isbnList)
                     .map(isbn -> ResponseCheckBooksByIsbnDto.builder()
                                                             .isbn(isbn)
                                                             .isInBook(
                                                                 bookRepository.existsByIsbn(isbn))
                                                             .build())
                     .collect(Collectors.toList());
    }

    /**
     * book 데이터를 받아서 isbn으로 조회 후 없으면 생성, 있으면 해당 데이터를 반환
     *
     * @param bookDto : RequestCreateBookDto
     * @return ResponseBookDto
     */
    @Override
    @Transactional
    public ResponseBookDto getOrCreateBookByBookData(RequestCreateBookDto bookDto) {
        // isbn으로 조회했을 때 결과
        Book bookByIsbn = bookRepository.findByIsbn(bookDto.isbn());

        // 만약 결과가 존재하면 그대로 반환
        if (bookByIsbn != null) {
            return bookMapper.toResponseDto(bookByIsbn);
        }

        // 없으면 생성
        return createBook(bookDto);
    }

    /**
     * 동적 쿼리 생성을 위한 메서드 확장성있게 다시 고칠 필요 있음
     *
     * @param filterDto : RequestSearchBooksFilterDto
     * @return List ResponseBookDto
     */
    @Override
    public List<ResponseBookDto> getBooksByCondition(RequestSearchBooksFilterDto filterDto) {
        List<Book> books = bookRepository.findByConditions(filterDto);

        return bookMapper.toResponseDtoList(books);
    }

    private Book findBookByIdWithException(Long bookId) {
        return bookRepository.findById(bookId)
                             .orElseThrow(() -> new BookNotFoundException(
                                 "Book not found with id : " + bookId));
    }
}
