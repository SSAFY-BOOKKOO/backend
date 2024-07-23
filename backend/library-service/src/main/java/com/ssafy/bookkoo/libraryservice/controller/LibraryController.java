package com.ssafy.bookkoo.libraryservice.controller;

import com.ssafy.bookkoo.libraryservice.entity.Library;
import com.ssafy.bookkoo.libraryservice.service.LibraryService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/libraries")
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryService libraryService;

    @GetMapping
    @Operation(summary = "서재 목록 조회", description = "서재 목록 조회 API")
    public List<Library> getLibraries() {
        return null;
    }

    @GetMapping("/{libraryId}")
    @Operation(summary = "서재 단일 조회", description = "서재 단일 조회 API")
    public Library getLibrary(@RequestParam Long id) {
        return null;
    }

//    @GetMapping("/{libraryId}/books/{bookId}")
//    @Operation(summary = "서재 내부 책 상세 조회", description = "서재 내부 책 상세 조회 API")
//    public Book getBook(@PathVariable Long libraryId, @PathVariable Long bookId) {
//        return null;
//    }

    @PatchMapping("/{libraryId}")
    @Operation(summary = "서재 수정", description = "서재 수정 API")
    public Library updateLibrary(@PathVariable Long libraryId, @RequestBody Library library) {
        return null;
    }

    @PostMapping("/{libraryId}/books")
    @Operation(summary = "서재에 책 등록", description = "서재에 책 등록하는 API")
    public Object addBookToLibrary(@PathVariable Long libraryId, @RequestBody Object book) {
        return null;
    }

    @GetMapping("/books/count")
    @Operation(summary = "사용자가 등록한 모든 책 개수", description = "사용자가 본인 서재에 등록한 모든 책 개수")
    public ResponseEntity<Integer> countBooks() {
        return null;
    }
}
