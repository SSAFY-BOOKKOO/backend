package com.ssafy.bookkoo.bookservice.controller;

import com.ssafy.bookkoo.bookservice.dto.book.RequestCreateBookDto;
import com.ssafy.bookkoo.bookservice.dto.book.RequestSearchBookMultiFieldDto;
import com.ssafy.bookkoo.bookservice.dto.book.ResponseBookDto;
import com.ssafy.bookkoo.bookservice.dto.book.ResponseCheckBooksByIsbnDto;
import com.ssafy.bookkoo.bookservice.service.book.BookService;
import com.ssafy.bookkoo.bookservice.util.AladinAPI.AladinAPISearchParams;
import com.ssafy.bookkoo.bookservice.util.AladinAPI.ResponseAladinAPI;
import com.ssafy.bookkoo.bookservice.util.AladinAPI.ResponseAladinSearchDetail;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ResponseEntity<ResponseBookDto> addBook(@RequestBody RequestCreateBookDto bookDto) {
        ResponseBookDto createdBook = bookService.createBook(bookDto);
        return ResponseEntity.ok()
                             .body(createdBook);
    }

//    @GetMapping
//    @Operation(summary = "책 목록 조회", description = "책 조회(필터링 포함)시 사용하는 API")
//    public ResponseEntity<List<ResponseBookDto>> getBooksByCondition(
//        @ModelAttribute RequestSearchBooksFilterDto filterDto
//    ) {
//        List<ResponseBookDto> books = bookService.getBooksByCondition(filterDto);
//        return ResponseEntity.ok()
//                             .body(books);
//    }

    /**
     * 복잡한 쿼리로 인한 Body 사용을 위해 PostMapping으로 바꾼 책 목록 조회 api
     *
     * @param filterDto : RequestSearchBookMultiFieldDto
     * @return List<ResponseBookDto>
     */
    @PostMapping("/search")
    @Operation(summary = "책 목록 조회", description = "책 조회(필터링 포함)시 사용하는 API")
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

    @GetMapping("/isbn/{isbn}")
    @Operation(summary = "isbn으로 책 조회", description = "책을 isbn으로 조회할 때 사용하는 API")
    public ResponseEntity<ResponseBookDto> getBookByIsbn(@PathVariable("isbn") String isbn) {
        ResponseBookDto bookDto = bookService.getBookByIsbn(isbn);
        return ResponseEntity.ok()
                             .body(bookDto);
    }

    @PostMapping("/isbn")
    @Operation(summary = "isbn으로 책 조회 또는 생성", description = "책을 isbn으로 조회하거나 존재하지 않으면 생성하는 API")
    public ResponseEntity<ResponseBookDto> getOrCreateBookByIsbn(@RequestBody RequestCreateBookDto bookDto) {
        ResponseBookDto bookResult = bookService.getOrCreateBookByBookData(bookDto);
        return ResponseEntity.ok()
                             .body(bookResult);
    }

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
    @Operation(summary = "알라딘 API 검색", description = "알라딘 API를 사용하여 책 검색")
    public ResponseEntity<ResponseAladinAPI> aladinSearchBooks(@Valid @ModelAttribute AladinAPISearchParams params)
        throws IOException, URISyntaxException, InterruptedException, ParseException {
        return ResponseEntity.ok()
                             .body(bookService.searchBooksFromAladin(params));
    }

    /**
     * 알라딘 API를 이용한 상세 검색
     *
     * @param isbn : isbn string
     * @return 상세 검색 반환
     */
    @GetMapping("/aladin/books/{isbn}")
    @Operation(summary = "알라딘 API 상세 검색", description = "알라딘 API를 사용하여 책 상세 검색")
    public ResponseEntity<ResponseAladinSearchDetail> aladinSearchBooks(@PathVariable String isbn)
        throws IOException, URISyntaxException, InterruptedException, ParseException {
        return ResponseEntity.ok()
                             .body(bookService.searchBookDetailFromAladin(isbn));
    }
}
