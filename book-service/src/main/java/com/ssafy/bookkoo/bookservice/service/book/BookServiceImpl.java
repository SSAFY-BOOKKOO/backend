package com.ssafy.bookkoo.bookservice.service.book;

import com.ssafy.bookkoo.bookservice.dto.aladin.AladinAPISearchParams;
import com.ssafy.bookkoo.bookservice.dto.aladin.ResponseAladinAPI;
import com.ssafy.bookkoo.bookservice.dto.aladin.ResponseAladinSearchDetail;
import com.ssafy.bookkoo.bookservice.dto.book.RequestCreateBookDto;
import com.ssafy.bookkoo.bookservice.dto.book.RequestSearchBookMultiFieldDto;
import com.ssafy.bookkoo.bookservice.dto.book.RequestSearchBooksFilterDto;
import com.ssafy.bookkoo.bookservice.dto.book.ResponseBookDto;
import com.ssafy.bookkoo.bookservice.dto.book.ResponseBookOfLibraryDto;
import com.ssafy.bookkoo.bookservice.dto.book.ResponseCheckBooksByIsbnDto;
import com.ssafy.bookkoo.bookservice.dto.stats.ResponseStatsCategoryDto;
import com.ssafy.bookkoo.bookservice.entity.Book;
import com.ssafy.bookkoo.bookservice.entity.Category;
import com.ssafy.bookkoo.bookservice.exception.BookCreateFailedException;
import com.ssafy.bookkoo.bookservice.exception.BookNotFoundException;
import com.ssafy.bookkoo.bookservice.exception.CategoryNotFoundException;
import com.ssafy.bookkoo.bookservice.mapper.BookMapper;
import com.ssafy.bookkoo.bookservice.repository.book.BookRepository;
import com.ssafy.bookkoo.bookservice.repository.category.CategoryRepository;
import com.ssafy.bookkoo.bookservice.util.AladinAPI.AladinAPIHandler;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * BookService의 구현체로, 책 관련 비즈니스 로직을 처리합니다.
 */
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final AladinAPIHandler aladinAPIHandler;
    private final BookMapper bookMapper;

    /**
     * 새로운 책을 생성합니다.
     *
     * @param bookDto 책 생성 요청 DTO
     * @return 생성된 책 응답 DTO
     */
    @Override
    @Transactional
    public ResponseBookDto createBook(RequestCreateBookDto bookDto) {
        // DTO를 엔티티로 변환
        Book book = bookMapper.toEntity(bookDto);

        Category category = categoryRepository.findById(bookDto
                                                  .category()
                                                  .getId()
                                              )
                                              .orElseThrow(
                                                  () -> new CategoryNotFoundException(
                                                      "category Not found")
                                              );

        book.setCategory(category);

        Book savedBook;
        try {
            // 책 저장
            savedBook = bookRepository.save(book);
        } catch (DataIntegrityViolationException e) {
            throw new BookCreateFailedException(e.getMessage());
        }

        // 엔티티를 DTO로 변환
        return bookMapper.toResponseDto(savedBook);
    }


    /**
     * 검색 조건을 기반으로 책 목록을 조회합니다.
     *
     * @param type    검색 종류 (title, author, publisher 등)
     * @param content 검색 내용
     * @param offset  페이지 오프셋
     * @param limit   페이지 내 개수
     * @return 검색된 책 응답 DTO 리스트
     */
    @Override
    @Transactional(readOnly = true)
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
     * 특정 책 ID를 기반으로 책 정보를 조회합니다.
     *
     * @param bookId 책 ID
     * @return 책 응답 DTO
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseBookDto getBook(Long bookId) {
        Book book = findBookByIdWithException(bookId);

        return bookMapper.toResponseDto(book);
    }

    /**
     * ISBN을 기반으로 책 정보를 조회합니다.
     *
     * @param isbn 책 ISBN
     * @return 책 응답 DTO
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseBookDto getBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn);

        return bookMapper.toResponseDto(book);
    }

    /**
     * 특정 책 ID를 기반으로 책을 삭제합니다.
     *
     * @param bookId 책 ID
     * @return 삭제된 책 응답 DTO
     */
    @Override
    @Transactional
    public ResponseBookDto deleteBook(Long bookId) {
        Book book = findBookByIdWithException(bookId);

        bookRepository.delete(book);
        return bookMapper.toResponseDto(book);
    }

    /**
     * 알라딘 API를 사용하여 책을 검색합니다.
     *
     * @param params 알라딘 API 검색 파라미터
     * @return 알라딘 API 응답 객체
     * @throws IOException          입출력 예외
     * @throws InterruptedException 인터럽트 예외
     * @throws URISyntaxException   URI 구문 예외
     */
    @Override
    public ResponseAladinAPI searchBooksFromAladin(
        Long memberId,
        AladinAPISearchParams params
    )
        throws IOException, InterruptedException, URISyntaxException {
        return aladinAPIHandler.searchBooks(memberId, params);
    }

    /**
     * 알라딘 API를 사용하여 특정 ISBN의 책 상세 정보를 검색합니다.
     *
     * @param isbn 책 ISBN
     * @return 알라딘 API 책 상세 응답 객체
     * @throws IOException          입출력 예외
     * @throws InterruptedException 인터럽트 예외
     * @throws URISyntaxException   URI 구문 예외
     */
    @Override
    public ResponseAladinSearchDetail searchBookDetailFromAladin(String isbn)
        throws IOException, InterruptedException, URISyntaxException {
        return aladinAPIHandler.searchBookDetail(isbn);
    }

    /**
     * 여러 조건을 적용하여 책 목록을 조회합니다.
     *
     * @param filterDto 여러 조건을 담은 검색 필터 DTO
     * @return 검색된 책 응답 DTO 리스트
     */
    @Override
    @Transactional(readOnly = true)
    public List<ResponseBookDto> getBooksByCondition(RequestSearchBookMultiFieldDto filterDto) {
        List<Book> books = bookRepository.findByConditions(filterDto);

        return bookMapper.toResponseDtoList(books);
    }

    /**
     * 내 서재의 책 단일 조회 시 사용
     *
     * @param bookId   BOok ID
     * @param memberId member ID
     * @return ResponseBookOfLibraryDto
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseBookOfLibraryDto getBookOfLibrary(
        Long bookId,
        Long memberId
    ) {

        return bookRepository.getBookOfLibrary(bookId, memberId);
    }

    /**
     * 책 ID 리스트로 카테고리 통계 내서 반환
     *
     * @param bookIds 책 ID 리스트
     * @return List<ResponseStatsCategoryDto>
     */
    @Override
    @Transactional(readOnly = true)
    public List<ResponseStatsCategoryDto> getBookCategoryStats(List<Long> bookIds) {

        return bookRepository.searchBookCategoryStats(bookIds);
    }

    /**
     * ISBN 리스트를 기반으로 책들이 존재하는지 여부를 확인합니다.
     *
     * @param isbnList ISBN 리스트
     * @return 책 존재 여부 응답 DTO 리스트
     */
    @Override
    @Transactional(readOnly = true)
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
     * 주어진 책 데이터를 기반으로 책을 조회하거나 존재하지 않으면 새로 생성합니다.
     *
     * @param bookDto 책 생성 요청 DTO
     * @return 생성되거나 조회된 책 응답 DTO
     */
    @Override
    @Transactional
    public ResponseBookDto getOrCreateBookByBookData(RequestCreateBookDto bookDto) {
        try {
            // isbn으로 조회했을 때 결과
            Book bookByIsbn = bookRepository.findByIsbn(bookDto.isbn());

            // 만약 결과가 존재하면 그대로 반환
            if (bookByIsbn != null) {
                return bookMapper.toResponseDto(bookByIsbn);
            }
            // 없으면 생성
            return createBook(bookDto);
        } catch (Exception e) {
            // 중복된 ISBN으로 인한 예외 발생 시 다시 조회하여 반환
            Book bookByIsbn = bookRepository.findByIsbn(bookDto.isbn());
            if (bookByIsbn != null) {
                return bookMapper.toResponseDto(bookByIsbn);
            }
            throw new BookCreateFailedException(e.getMessage()); // 다른 예외는 다시 던짐
        }
    }

    /**
     * 검색 필터 DTO를 기반으로 책 목록을 조회합니다.
     *
     * @param filterDto 검색 필터 DTO
     * @return 검색된 책 응답 DTO 리스트
     */
    @Override
    @Transactional(readOnly = true)
    public List<ResponseBookDto> getBooksByCondition(RequestSearchBooksFilterDto filterDto) {
        List<Book> books = bookRepository.findByConditions(filterDto);

        return bookMapper.toResponseDtoList(books);
    }

    /**
     * 주어진 책 ID로 책을 찾지 못했을 때 예외를 발생시키는 헬퍼 메서드
     *
     * @param bookId 책 ID
     * @return 조회된 책 엔티티
     */
    @Transactional(readOnly = true)
    protected Book findBookByIdWithException(Long bookId) {
        return bookRepository.findById(bookId)
                             .orElseThrow(() -> new BookNotFoundException(
                                 "Book not found with id : " + bookId));
    }
}
