package com.ssafy.bookkoo.bookservice.controller;

import com.ssafy.bookkoo.bookservice.dto.RequestCreateBookDto;
import com.ssafy.bookkoo.bookservice.dto.RequestSearchBookFilterDto;
import com.ssafy.bookkoo.bookservice.dto.ResponseBookDto;
import com.ssafy.bookkoo.bookservice.service.BookService;
import com.ssafy.bookkoo.bookservice.util.AladinAPI.AladinAPISearchParams;
import com.ssafy.bookkoo.bookservice.util.AladinAPI.ResponseAladinAPI;
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

    @GetMapping
    @Operation(summary = "책 목록 조회", description = "책 조회(필터링 포함)시 사용하는 API")
    public ResponseEntity<List<ResponseBookDto>> getBooks(
        @ModelAttribute RequestSearchBookFilterDto filterDto
    ) {
        List<ResponseBookDto> books = bookService.getBooks(filterDto.type(), filterDto.content(),
            filterDto.offset(), filterDto.limit());
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
    @GetMapping("/api/search")
    @Operation(summary = "알라딘 API 검색", description = "알라딘 API를 사용하여 책 검색")
    public ResponseAladinAPI aladinSearchBooks(@Valid @ModelAttribute AladinAPISearchParams params)
        throws IOException, URISyntaxException, InterruptedException, ParseException {
        return bookService.searchBooksFromAladin(params);
    }
}
