package com.ssafy.bookkoo.libraryservice.client;

import com.ssafy.bookkoo.libraryservice.dto.other.RequestCreateBookDto;
import com.ssafy.bookkoo.libraryservice.dto.other.RequestSearchBookMultiFieldDto;
import com.ssafy.bookkoo.libraryservice.dto.other.ResponseBookDto;
import com.ssafy.bookkoo.libraryservice.dto.other.ResponseBookOfLibraryDto;
import com.ssafy.bookkoo.libraryservice.dto.other.ResponseCheckBooksByIsbnDto;
import com.ssafy.bookkoo.libraryservice.dto.stats.ResponseStatsCategoryDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * BookService와 통신하기 위한 FeignClient 인터페이스입니다.
 */
@FeignClient(name = "book-service")
public interface BookServiceClient {

    String prefix = "/books";

    /**
     * 책을 추가하는 메서드입니다.
     *
     * @param requestBookDto 책 생성 요청 DTO
     * @return 생성된 책 응답 DTO
     */
    @PostMapping(prefix)
    ResponseBookDto addBook(@RequestBody RequestCreateBookDto requestBookDto);

    /**
     * 조건을 기반으로 책 목록을 조회하는 메서드입니다.
     *
     * @param filter 검색 조건 DTO
     * @return 검색된 책 응답 DTO 리스트
     */
    @PostMapping(prefix + "/search")
    List<ResponseBookDto> getBooksByCondition(
        @RequestBody RequestSearchBookMultiFieldDto filter
    );

    /**
     * 특정 책 ID를 기반으로 책 정보를 조회하는 메서드입니다.
     *
     * @param bookId 책 ID
     * @return 조회된 책 응답 DTO
     */
    @GetMapping(prefix + "/{bookId}")
    ResponseBookDto getBookById(@PathVariable("bookId") String bookId);

    /**
     * ISBN을 기반으로 책 정보를 조회하는 메서드입니다.
     *
     * @param isbn 책 ISBN
     * @return 조회된 책 응답 DTO
     */
    @GetMapping(prefix + "/isbn/{isbn}")
    ResponseBookDto getBookByIsbn(@PathVariable("isbn") String isbn);

    /**
     * 책 데이터를 기반으로 ISBN으로 책을 조회하거나 존재하지 않으면 새로 생성하는 메서드입니다.
     *
     * @param requestBookDto 책 생성 요청 DTO
     * @return 생성되거나 조회된 책 응답 DTO
     */
    @PostMapping(prefix + "/isbn")
    ResponseBookDto getOrCreateBookByBookData(@RequestBody RequestCreateBookDto requestBookDto);

    /**
     * ISBN 리스트를 기반으로 책들이 존재하는지 여부를 확인하는 메서드입니다.
     *
     * @param isbnList ISBN 리스트
     * @return 책 존재 여부 응답 DTO 리스트
     */
    @PostMapping(prefix + "/check-books")
    List<ResponseCheckBooksByIsbnDto> checkBooksByIsbn(@RequestBody String[] isbnList);
    
    @GetMapping(prefix + "/{bookId}/me")
    ResponseBookOfLibraryDto getBookOfLibrary(
        @PathVariable Long bookId,
        @RequestParam Long memberId
    );

    /**
     * bookId 리스트를 주고 카테고리 통계를 받아오은 메서드
     *
     * @param bookIds bookId 리스트
     * @return List<ResponseStatsCategoryDto>
     */
    @GetMapping(prefix + "/categories/stats")
    List<ResponseStatsCategoryDto> getBookCategoryStats(@RequestParam List<Long> bookIds);
}
