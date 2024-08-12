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
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import org.json.simple.parser.ParseException;
import org.springframework.transaction.annotation.Transactional;

/**
 * 책 서비스 인터페이스로, 책 관련 비즈니스 로직을 정의합니다.
 */
public interface BookService {

    /**
     * 새로운 책을 생성합니다.
     *
     * @param bookDto 책 생성 요청 DTO
     * @return 생성된 책 응답 DTO
     */
    @Transactional
    ResponseBookDto createBook(RequestCreateBookDto bookDto);

    /**
     * 검색 조건을 기반으로 책 목록을 조회합니다.
     *
     * @param type    검색 종류 (title, author, publisher 등)
     * @param content 검색 내용
     * @param offset  페이지 오프셋
     * @param limit   페이지 내 개수
     * @return 검색된 책 응답 DTO 리스트
     */
    @Transactional
    List<ResponseBookDto> getBooks(String type, String content, int offset, int limit);

    /**
     * 특정 책 ID를 기반으로 책 정보를 조회합니다.
     *
     * @param bookId 책 ID
     * @return 책 응답 DTO
     */
    @Transactional
    ResponseBookDto getBook(Long bookId);

    /**
     * ISBN을 기반으로 책 정보를 조회합니다.
     *
     * @param isbn 책 ISBN
     * @return 책 응답 DTO
     */
    @Transactional
    ResponseBookDto getBookByIsbn(String isbn);

    /**
     * 특정 책 ID를 기반으로 책을 삭제합니다.
     *
     * @param bookId 책 ID
     * @return 삭제된 책 응답 DTO
     */
    @Transactional
    ResponseBookDto deleteBook(Long bookId);

    /**
     * 알라딘 API를 사용하여 책을 검색합니다.
     *
     * @param params 알라딘 API 검색 파라미터
     * @return 알라딘 API 응답 객체
     * @throws IOException          입출력 예외
     * @throws InterruptedException 인터럽트 예외
     * @throws URISyntaxException   URI 구문 예외
     * @throws ParseException       파싱 예외
     */
    ResponseAladinAPI searchBooksFromAladin(Long memberId, AladinAPISearchParams params)
        throws IOException, InterruptedException, URISyntaxException, ParseException;

    /**
     * ISBN 리스트를 기반으로 책들이 존재하는지 여부를 확인합니다.
     *
     * @param isbnList ISBN 리스트
     * @return 책 존재 여부 응답 DTO 리스트
     */
    @Transactional
    List<ResponseCheckBooksByIsbnDto> checkBooksByIsbn(String[] isbnList);

    /**
     * 주어진 책 데이터를 기반으로 책을 조회하거나 존재하지 않으면 새로 생성합니다.
     *
     * @param bookDto 책 생성 요청 DTO
     * @return 생성되거나 조회된 책 응답 DTO
     */
    @Transactional
    ResponseBookDto getOrCreateBookByBookData(RequestCreateBookDto bookDto);

    /**
     * 검색 필터 DTO를 기반으로 책 목록을 조회합니다.
     *
     * @param filterDto 검색 필터 DTO
     * @return 검색된 책 응답 DTO 리스트
     */
    @Transactional
    List<ResponseBookDto> getBooksByCondition(RequestSearchBooksFilterDto filterDto);

    /**
     * 알라딘 API를 사용하여 특정 ISBN의 책 상세 정보를 검색합니다.
     *
     * @param isbn 책 ISBN
     * @return 알라딘 API 책 상세 응답 객체
     * @throws IOException          입출력 예외
     * @throws InterruptedException 인터럽트 예외
     * @throws URISyntaxException   URI 구문 예외
     * @throws ParseException       파싱 예외
     */
    ResponseAladinSearchDetail searchBookDetailFromAladin(String isbn)
        throws IOException, InterruptedException, URISyntaxException, ParseException;

    /**
     * 여러 조건을 적용하여 책 목록을 조회합니다.
     *
     * @param filterDto 여러 조건을 담은 검색 필터 DTO
     * @return 검색된 책 응답 DTO 리스트
     */
    @Transactional(readOnly = true)
    List<ResponseBookDto> getBooksByCondition(RequestSearchBookMultiFieldDto filterDto);

    /**
     * 내 서재의 책 단일 조회 시 사용
     *
     * @param bookId   BOok ID
     * @param memberId member ID
     * @return ResponseBookOfLibraryDto
     */
    ResponseBookOfLibraryDto getBookOfLibrary(Long bookId, Long memberId);

    /**
     * 책 ID 리스트로 카테고리 통계 내서 반환
     *
     * @param bookIds 책 ID 리스트
     * @return List<ResponseStatsCategoryDto>
     */
    List<ResponseStatsCategoryDto> getBookCategoryStats(List<Long> bookIds);
}
