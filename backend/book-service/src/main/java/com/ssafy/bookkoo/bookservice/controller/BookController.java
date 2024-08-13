package com.ssafy.bookkoo.bookservice.controller;

import com.ssafy.bookkoo.bookservice.dto.aladin.AladinAPISearchParams;
import com.ssafy.bookkoo.bookservice.dto.aladin.ResponseAladinAPI;
import com.ssafy.bookkoo.bookservice.dto.aladin.ResponseAladinSearchDetail;
import com.ssafy.bookkoo.bookservice.dto.book.RequestCreateBookDto;
import com.ssafy.bookkoo.bookservice.dto.book.RequestSearchBookMultiFieldDto;
import com.ssafy.bookkoo.bookservice.dto.book.ResponseBookDto;
import com.ssafy.bookkoo.bookservice.dto.book.ResponseBookOfLibraryDto;
import com.ssafy.bookkoo.bookservice.dto.book.ResponseCheckBooksByIsbnDto;
import com.ssafy.bookkoo.bookservice.dto.stats.ResponseStatsCategoryDto;
import com.ssafy.bookkoo.bookservice.service.book.BookService;
import com.ssafy.bookkoo.bookservice.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    /**
     * 책 생성 API
     *
     * @param bookDto : RequestCreateBookDto 형식의 책 생성 데이터
     * @return ResponseBookDto 형식의 책 반환 데이터
     */
    @PostMapping
    @Operation(summary = "책 생성",
        description = "책 생성시 사용하는 API"
    )
    public ResponseEntity<ResponseBookDto> addBook(
        @Valid @RequestBody RequestCreateBookDto bookDto
    ) {
        ResponseBookDto createdBook = bookService.createBook(bookDto);
        return ResponseEntity.ok()
                             .body(createdBook);
    }

    /**
     * 복잡한 쿼리로 인한 Body 사용을 위해 PostMapping으로 바꾼 책 목록 조회 api
     *
     * @param filterDto : RequestSearchBookMultiFieldDto
     * @return List<ResponseBookDto>
     */
    @PostMapping("/search")
    @Operation(
        summary = "책 목록 조회",
        description = """
            책 조회(필터링 포함)시 사용하는 API(conditions 를 빈 리스트로 보내면 조건 없는 필터링)


            <b>Input</b>:
            | Name | Type  | Description |
            |-----|-----|-------|
            | conditions | SearchBookConditionDto | 조건 데이터 구조 |
            | limit | int | 한 번의 요청에 나올 최대 데이터 개수 |
            | offset | int | 페이지 넘버 |

            <br>
            <br>

            <b>SearchBookConditionDto</b>:
            | Name | Type  | Description |
            |-----|-----|-------|
            | field | string | 검색할 컬럼 이름(title, author, publisher, id, ... 등) |
            | values | List(string) | 검색할 값 ex) ["어린왕자","냠냠",...] |

            <b>Output</b>:
            <br>
                type: _description_

            | Var | Type | Description |
            |-----|-----|-------|
            |  |  |  |
            """
    )
    public ResponseEntity<List<ResponseBookDto>> searchBooksByCondition(
        @Valid @RequestBody RequestSearchBookMultiFieldDto filterDto
    ) {
        List<ResponseBookDto> books = bookService.getBooksByCondition(filterDto);
        return ResponseEntity.ok()
                             .body(books);
    }

    /**
     * 책 단일 조회 API
     *
     * @param bookId : 조회할 책 ID
     * @return ResponseBookDto 형식의 책 DTO
     */
    @GetMapping("/{bookId}")
    @Operation(summary = "책 단일 조회", description = "책 단일 조회시 사용하는 API")
    public ResponseEntity<ResponseBookDto> getBook(@PathVariable("bookId") Long bookId) {
        ResponseBookDto bookDto = bookService.getBook(bookId);
        return ResponseEntity.ok()
                             .body(bookDto);
    }

    /**
     * 서재 내 책 단일 조회
     *
     * @param bookId book id
     * @return ResponseBookOfLibraryDto (book + review)
     */
    @GetMapping("/{bookId}/me")
    @Operation(summary = "서재 내 책 단일 조회", description = "서재 내 책 단일 조회 시 사용하는 API(내가 쓴 한줄평도 포함)")
    public ResponseEntity<ResponseBookOfLibraryDto> getBookByIsbn(
        @PathVariable("bookId") Long bookId,
        @RequestParam("memberId") Long memberId
    ) {
        return ResponseEntity.ok()
                             .body(bookService.getBookOfLibrary(bookId, memberId));
    }

    /**
     * isbn 으로 책 조회 하는 API
     *
     * @param isbn : isbn
     * @return : ResponseBookDto
     */
    @GetMapping("/isbn/{isbn}")
    @Operation(summary = "isbn으로 책 조회", description = "책을 isbn으로 조회할 때 사용하는 API")
    public ResponseEntity<ResponseBookDto> getBookByIsbn(@PathVariable("isbn") String isbn) {
        ResponseBookDto bookDto = bookService.getBookByIsbn(isbn);
        return ResponseEntity.ok()
                             .body(bookDto);
    }

    /**
     * isbn 으로 책 조회 또는 생성하는 API, 사용처 : 서재 서비스에서 책 등록 시 호출
     *
     * @param bookDto : bookDto(id 제외한 책 정보 )
     * @return ResponseBookDto
     */
    @PostMapping("/isbn")
    @Operation(summary = "isbn으로 책 조회 또는 생성", description = "책을 isbn으로 조회하거나 존재하지 않으면 생성하는 API")
    public ResponseEntity<ResponseBookDto> getOrCreateBookByIsbn(
        @Valid @RequestBody RequestCreateBookDto bookDto
    ) {
        ResponseBookDto bookResult = bookService.getOrCreateBookByBookData(bookDto);
        return ResponseEntity.ok()
                             .body(bookResult);
    }

    /**
     * ISBN 리스트를 받아서 해당 책 데이터가 DB 내에 존재하는지 여부를 반환
     *
     * @param isbnList : isbn 리스트
     * @return : List<ResponseCheckBooksByIsbnDto>
     */
    @PostMapping("/check-books")
    @Operation(summary = "ISBN 리스트로 DB내 존재하는지 여부 조회", description = "ISBN 리스트로 DB내 존재하는지 여부를 확인하는 API")
    public ResponseEntity<List<ResponseCheckBooksByIsbnDto>> checkBooksByIsbn(@RequestBody String[] isbnList) {
        List<ResponseCheckBooksByIsbnDto> booksStatus = bookService.checkBooksByIsbn(
            isbnList);
        return ResponseEntity.ok()
                             .body(booksStatus);
    }

    /**
     * 책 삭제 API
     *
     * @param bookId : 삭제할 책 ID
     * @return 삭제된 책 데이터
     */
    @DeleteMapping("/{bookId}")
    @Operation(summary = "책 삭제", description = "책 삭제시 사용하는 API")
    public ResponseEntity<ResponseBookDto> deleteBook(@PathVariable("bookId") Long bookId) {
        ResponseBookDto bookDto = bookService.deleteBook(bookId);
        return ResponseEntity.ok()
                             .body(bookDto);
    }

    /**
     * 알라딘 API를 사용한 검색
     *
     * @param params : AladinAPISearchParams
     * @return 검색 결과 반환
     */
    @GetMapping("/aladin/books")
    @Operation(summary = "알라딘 API 검색(프론트에서 도서 검색 시 사용)", description = "알라딘 API를 사용하여 책 검색")
    public ResponseEntity<ResponseAladinAPI> aladinSearchBooks(
        @RequestHeader HttpHeaders headers,
        @Valid @ModelAttribute AladinAPISearchParams params
    )
        throws IOException, URISyntaxException, InterruptedException, ParseException {
        Long memberId = CommonUtil.getMemberId(headers);
        return ResponseEntity.ok()
                             .body(bookService.searchBooksFromAladin(memberId, params));
    }

    /**
     * 알라딘 API를 이용한 상세 검색
     *
     * @param isbn : isbn string
     * @return 상세 검색 반환
     */
    @GetMapping("/aladin/books/{isbn}")
    @Operation(summary = "알라딘 API 상세 검색(프론트에서 도서 검색 후 상세 조회 시 사용)", description = "알라딘 API를 사용하여 책 상세 검색")
    public ResponseEntity<ResponseAladinSearchDetail> aladinSearchBooks(@PathVariable String isbn)
        throws IOException, URISyntaxException, InterruptedException, ParseException {
        return ResponseEntity.ok()
                             .body(bookService.searchBookDetailFromAladin(isbn));
    }

    /**
     * 책 ID 리스트로 카테고리 통계 내서 반환
     *
     * @param bookIds 책 ID 리스트
     * @return List<ResponseStatsCategoryDto>
     */
    @GetMapping("/categories/stats")
    @Operation(summary = "서비스 통신 : 책 ID 리스트로 카테고리 통계 내서 반환", description = "서비스 통신 : 책 ID로 카테고리 통계 내서 반환")
    public ResponseEntity<List<ResponseStatsCategoryDto>> getBookCategoryStats(
        @RequestParam List<Long> bookIds
    ) {
        return ResponseEntity.ok()
                             .body(bookService.getBookCategoryStats(bookIds));
    }
}
