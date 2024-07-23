package com.ssafy.bookkoo.libraryservice.controller;

import com.ssafy.bookkoo.libraryservice.dto.RequestCreateLibraryDto;
import com.ssafy.bookkoo.libraryservice.dto.RequestLibraryBookMapperCreateDto;
import com.ssafy.bookkoo.libraryservice.dto.RequestUpdateLibraryDto;
import com.ssafy.bookkoo.libraryservice.dto.ResponseLibraryDto;
import com.ssafy.bookkoo.libraryservice.service.LibraryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
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

    @PostMapping
    @Operation(summary = "서재 생성", description = "서재 생성 API")
    public ResponseEntity<ResponseLibraryDto> createLibrary(
        @RequestBody RequestCreateLibraryDto dto,
        @RequestParam Long memberId // 임시
    ) {
        return ResponseEntity.ok()
                             .body(libraryService.addLibrary(dto, memberId));
    }

    @GetMapping
    @Operation(summary = "서재 목록 조회", description = "해당 사용자의 서재 목록 조회 API")
    // 임시로 memberId 받는걸로
    public ResponseEntity<List<ResponseLibraryDto>> getLibraries(@RequestParam Long memberId) {
        return ResponseEntity.ok()
                             .body(libraryService.getLibrariesOfMember(memberId));
    }

    @GetMapping("/{libraryId}")
    @Operation(summary = "서재 단일 조회", description = "서재 단일 조회 API")
    public ResponseEntity<ResponseLibraryDto> getLibrary(@PathVariable Long libraryId) {
        return ResponseEntity.ok()
                             .body(libraryService.getLibrary(libraryId));
    }

    @PatchMapping("/{libraryId}")
    @Operation(summary = "서재 수정", description = "서재 수정 API")
    public ResponseEntity<ResponseLibraryDto> updateLibrary(
        @PathVariable Long libraryId,
        @RequestBody RequestUpdateLibraryDto libraryDto
    ) {
        return ResponseEntity.ok()
                             .body(libraryService.updateLibrary(libraryId, libraryDto));
    }

    @PostMapping("/{libraryId}/books")
    @Operation(summary = "서재에 책 등록", description = "서재에 책 등록하는 API")
    public ResponseEntity<Boolean> addBookToLibrary(
        @PathVariable Long libraryId,
        @Valid @RequestBody RequestLibraryBookMapperCreateDto libraryBookMapperDto,
        @RequestParam Long memberId // 임시
    ) {
        libraryService.addBookToLibrary(libraryId,
            libraryBookMapperDto, memberId);
        return ResponseEntity.ok()
                             .body(true);
    }

    @GetMapping("/books/count")
    @Operation(summary = "사용자가 등록한 모든 책 개수", description = "사용자가 본인 서재에 등록한 모든 책 개수")
    // 임시로 memberId 받는걸로
    public ResponseEntity<Integer> countBooks(@RequestParam Long memberId) {
        // 그런것들
        return ResponseEntity.ok()
                             .body(libraryService.countBooksInLibrary(memberId));
    }
}
