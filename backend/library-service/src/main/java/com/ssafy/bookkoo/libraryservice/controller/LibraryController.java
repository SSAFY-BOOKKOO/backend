package com.ssafy.bookkoo.libraryservice.controller;

import com.ssafy.bookkoo.libraryservice.dto.RequestCreateLibraryDto;
import com.ssafy.bookkoo.libraryservice.dto.RequestLibraryBookMapperCreateDto;
import com.ssafy.bookkoo.libraryservice.dto.RequestLibraryBookMapperUpdateDto;
import com.ssafy.bookkoo.libraryservice.dto.RequestSearchBookMultiFieldDto;
import com.ssafy.bookkoo.libraryservice.dto.RequestUpdateLibraryDto;
import com.ssafy.bookkoo.libraryservice.dto.ResponseBookDto;
import com.ssafy.bookkoo.libraryservice.dto.ResponseLibraryDto;
import com.ssafy.bookkoo.libraryservice.entity.Status;
import com.ssafy.bookkoo.libraryservice.service.LibraryService;
import com.ssafy.bookkoo.libraryservice.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
        @RequestHeader HttpHeaders headers,
        @RequestBody RequestCreateLibraryDto dto
    ) {
        Long memberId = CommonUtil.getMemberId(headers);

        return ResponseEntity.ok()
                             .body(libraryService.addLibrary(dto, memberId));
    }

    @GetMapping
    @Operation(summary = "해당 사용자의 서재 목록 조회", description = "사용자의 닉네임을 받아 해당 사용자의 서재 목록 조회 API")
    public ResponseEntity<List<ResponseLibraryDto>> getLibraries(@RequestParam String nickname) {
        return ResponseEntity.ok()
                             .body(libraryService.getLibrariesOfMember(nickname));
    }

    @GetMapping("/{libraryId}")
    @Operation(summary = "서재 단일 조회", description = "서재 단일 조회 API")
    public ResponseEntity<ResponseLibraryDto> getLibrary(
        @PathVariable Long libraryId,
        @RequestParam(required = false) Status filter
    ) {
        return ResponseEntity.ok()
                             .body(libraryService.getLibrary(libraryId, filter));
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
        @RequestHeader HttpHeaders headers,
        @PathVariable Long libraryId,
        @Valid @RequestBody RequestLibraryBookMapperCreateDto libraryBookMapperDto
    ) {
        Long memberId = CommonUtil.getMemberId(headers);

        libraryService.addBookToLibrary(libraryId,
            libraryBookMapperDto, memberId);
        return ResponseEntity.ok()
                             .body(true);
    }

    @PutMapping("/{libraryId}/books")
    @Operation(summary = "서재 내 책 수정(순서 등)", description = "서재 내 책들을 수정하는 API")
    public ResponseEntity<Boolean> updateBookToLibrary(
        @RequestHeader HttpHeaders headers,
        @PathVariable Long libraryId,
        @RequestBody List<RequestLibraryBookMapperUpdateDto> lbmDto
    ) {
        Long memberId = CommonUtil.getMemberId(headers);

        return ResponseEntity.ok()
                             .body(libraryService.updateLibraryBookMappers(libraryId, lbmDto,
                                 memberId));
    }

    @GetMapping("/me/books/count")
    @Operation(summary = "사용자가 등록한 모든 책 개수", description = "사용자가 본인 서재에 등록한 모든 책 개수")
    public ResponseEntity<Integer> countBooks(@RequestHeader HttpHeaders headers) {
        Long memberId = CommonUtil.getMemberId(headers);

        return ResponseEntity.ok()
                             .body(libraryService.countBooksInLibrary(memberId));
    }

    @PostMapping("/me/books/search")
    @Operation(summary = "사용자가 등록한 책 반환", description = "사용자가 본인 서재에 등록한 책 대상 ")
    public ResponseEntity<List<ResponseBookDto>> getMyBooks(
        @RequestHeader HttpHeaders headers,
        @RequestBody RequestSearchBookMultiFieldDto searchDto
    ) {
        Long memberId = CommonUtil.getMemberId(headers);

        return ResponseEntity.ok()
                             .body(libraryService.getMyBooks(memberId, searchDto));
    }
}
