package com.ssafy.bookkoo.libraryservice.controller;

import com.ssafy.bookkoo.libraryservice.dto.library.RequestCreateLibraryDto;
import com.ssafy.bookkoo.libraryservice.dto.library.RequestUpdateLibraryDto;
import com.ssafy.bookkoo.libraryservice.dto.library.ResponseLibraryDto;
import com.ssafy.bookkoo.libraryservice.dto.library_book.RequestLibraryBookMapperCreateDto;
import com.ssafy.bookkoo.libraryservice.dto.library_book.RequestLibraryBookMapperUpdateDto;
import com.ssafy.bookkoo.libraryservice.dto.library_book.ResponseLibraryBookDto;
import com.ssafy.bookkoo.libraryservice.dto.other.RequestSearchBookMultiFieldDto;
import com.ssafy.bookkoo.libraryservice.dto.other.ResponseBookDto;
import com.ssafy.bookkoo.libraryservice.dto.other.ResponseRecentFiveBookDto;
import com.ssafy.bookkoo.libraryservice.entity.Status;
import com.ssafy.bookkoo.libraryservice.service.library.LibraryService;
import com.ssafy.bookkoo.libraryservice.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

/**
 * 서재 관련 API를 제공하는 컨트롤러 클래스입니다.
 */
@RestController
@RequestMapping("/libraries")
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryService libraryService;

    /**
     * 서재를 생성합니다.
     *
     * @param headers HTTP 헤더
     * @param dto     서재 생성 요청 DTO
     * @return 생성된 서재 응답 DTO
     */
    @PostMapping
    @Operation(summary = "서재 생성", description = "서재 생성 API")
    public ResponseEntity<ResponseLibraryDto> createLibrary(
        @RequestHeader HttpHeaders headers,
        @Valid @RequestBody RequestCreateLibraryDto dto
    ) {
        Long memberId = CommonUtil.getMemberId(headers);

        return ResponseEntity.ok()
                             .body(libraryService.addLibrary(dto, memberId));
    }

    /**
     * 해당 사용자의 서재 목록을 조회합니다.
     *
     * @param nickname 사용자의 닉네임
     * @return 사용자의 서재 목록 응답 DTO 리스트
     */
    @GetMapping
    @Operation(summary = "해당 사용자의 서재 목록 조회", description = "사용자의 닉네임을 받아 해당 사용자의 서재 목록 조회 API<br> 주의사항 : book 데이터는 비어있음")
    public ResponseEntity<List<ResponseLibraryDto>> getLibraries(@RequestParam String nickname) {
        return ResponseEntity.ok()
                             .body(libraryService.getLibrariesOfMember(nickname));
    }

    /**
     * 본인의 서재 목록을 조회합니다.
     *
     * @return 사용자의 서재 목록 응답 DTO 리스트
     */
    @GetMapping("/me")
    @Operation(summary = "본인의 서재 목록 조회", description = "본인의 서재 목록 조회 API")
    public ResponseEntity<List<ResponseLibraryDto>> getMyLibraries(
        @RequestHeader HttpHeaders headers
    ) {
        Long memberId = CommonUtil.getMemberId(headers);
        return ResponseEntity.ok()
                             .body(libraryService.getMyLibraries(memberId));
    }

    /**
     * 서재 단일 조회 API
     *
     * @param libraryId 서재 ID
     * @param filter    상태 필터
     * @return 조회된 서재 응답 DTO
     */
    @GetMapping("/{libraryId}")
    @Operation(
        summary = "서재 단일 조회",
        description = """
            서재 단일 조회(프론트에서 서재 조회 시 사용)


            <b>Input</b>:
            | Name | Type  | Description |
            |-----|-----|-------|
            | libraryId | number | 서재 ID |
            | filter | Status(READ, READING, DIB) | 상태. 안 줄경우 ALL 로 간주 |
            | page | number | page: 페이지 |
            | size | number | size: 한페이지 내 들어갈 <b>책</b> 개수 |
            | sort | list(string) | 생략하기

            <br>
            <br>

            <b>Output</b>:
            <br>
                type: _description_

            | Var | Type | Description |
            |-----|-----|-------|
            |  |  |  |
            """
    )
    public ResponseEntity<ResponseLibraryDto> getLibrary(
        @PathVariable Long libraryId,
        @RequestParam(required = false) Status filter,
        @PageableDefault(size = 21) // 기본 사이즈 21로 설정
        Pageable pageable
    ) {
        // pageable이 null이면 기본 PageRequest 생성
        if (pageable == null) {
            pageable = PageRequest.of(0, 21); // 첫 번째 페이지와 기본 사이즈 21로 설정
        }
        return ResponseEntity.ok()
                             .body(libraryService.getLibrary(libraryId, filter, pageable));
    }

    /**
     * 서재를 수정합니다.
     *
     * @param libraryId  서재 ID
     * @param libraryDto 서재 수정 요청 DTO
     * @return 수정된 서재 응답 DTO
     */
    @PatchMapping("/{libraryId}")
    @Operation(summary = "서재 수정", description = "서재 수정 API")
    public ResponseEntity<ResponseLibraryDto> updateLibrary(
        @RequestHeader HttpHeaders headers,
        @PathVariable Long libraryId,
        @RequestBody RequestUpdateLibraryDto libraryDto
    ) {
        Long memberId = CommonUtil.getMemberId(headers);
        return ResponseEntity.ok()
                             .body(libraryService.updateLibrary(memberId, libraryId, libraryDto));
    }

    /**
     * 서재를 삭제합니다
     *
     * @param libraryId 서재 ID
     * @return true / false
     */
    @DeleteMapping("/{libraryId}")
    @Operation(summary = "서재 삭제", description = "서재 삭제 API")
    public ResponseEntity<Boolean> deleteLibrary(
        @RequestHeader HttpHeaders headers,
        @PathVariable Long libraryId
    ) {
        Long memberId = CommonUtil.getMemberId(headers);
        return ResponseEntity.ok()
                             .body(libraryService.deleteLibrary(memberId, libraryId));
    }

    /**
     * 서재에 책을 등록합니다.
     *
     * @param headers              HTTP 헤더
     * @param libraryId            서재 ID
     * @param libraryBookMapperDto 서재 책 매핑 생성 요청 DTO
     * @return 등록 성공 여부
     */
    @PostMapping("/{libraryId}/books")
    @Operation(summary = "서재에 책 등록", description = "서재에 책 등록하는 API")
    public ResponseEntity<Boolean> addBookToLibrary(
        @RequestHeader HttpHeaders headers,
        @PathVariable Long libraryId,
        @Valid @RequestBody RequestLibraryBookMapperCreateDto libraryBookMapperDto
    ) {
        Long memberId = CommonUtil.getMemberId(headers);

        libraryService.addBookToLibrary(libraryId, libraryBookMapperDto, memberId);
        return ResponseEntity.ok()
                             .body(true);
    }

    /**
     * 서재에 책 색상 변경
     *
     * @param headers   HTTP 헤더
     * @param libraryId 서재 ID
     * @param bookId    책 ID
     * @return 바뀐 데이터
     */
    @PatchMapping("/{libraryId}/books/{bookId}")
    @Operation(summary = "서재에 책 (색상 수정)/(서재 이동)", description = "서재에 있던 책 색상 수정 또는 서재 이동하는 API")
    public ResponseEntity<HttpStatus> updateBookColorFromLibrary(
        @RequestHeader HttpHeaders headers,
        @PathVariable Long libraryId,
        @PathVariable Long bookId,
        @RequestParam(required = false) Long targetLibraryId,
        @RequestParam(required = false) String bookColor
    ) {
        Long memberId = CommonUtil.getMemberId(headers);

        // bookColor 가 들어오면
        if (bookColor != null) {
            libraryService.updateBookColorFromLibrary(memberId, libraryId, bookId, bookColor);
        }
        // targetLibraryId가 들어오면
        if (targetLibraryId != null) {
            libraryService.updateBookLibraryIdFromLibrary(memberId, libraryId, bookId,
                targetLibraryId);
        }
        return ResponseEntity.ok()
                             .build();
    }

    /**
     * 서재에 책을 뺍니다
     *
     * @param headers   HTTP 헤더
     * @param libraryId 서재 ID
     * @param bookId    책 ID
     * @return 204 (no content)
     */
    @DeleteMapping("/{libraryId}/books/{bookId}")
    @Operation(summary = "서재에 책 삭제", description = "서재에 있던 책 삭제하는 API <br> 삭제 완료시 204 코드(no content)")
    public ResponseEntity<HttpStatus> deleteBookFromLibrary(
        @RequestHeader HttpHeaders headers,
        @PathVariable Long libraryId,
        @PathVariable Long bookId
    ) {
        Long memberId = CommonUtil.getMemberId(headers);
        libraryService.deleteBookFromLibrary(memberId, libraryId, bookId);
        return ResponseEntity.noContent()
                             .build();
    }

    /**
     * 서재 안의 책 단일 조회(프론트에서 서재 내 책 단일 조회시 사용)
     *
     * @param libraryId libraryId
     * @param bookId    bookId
     * @param nickname  사용자 닉네임
     * @return ResponseLibraryBookDto
     */
    @GetMapping("/{libraryId}/books/{bookId}")
    @Operation(
        summary = "서재 내 책 단일 조회(프론트에서 서재 내 책 단일 조회 시 사용)",
        description = """
            서재 내 책 단일 조회(프론트에서 서재 내 책 단일 조회 시 사용)


            <b>Input</b>:
            | Name | Type  | Description |
            |-----|-----|-------|
            | libraryId | number | 서재 ID |
            | bookId | number | 책 ID |
            | nickname | string | 조회하려는 서재 주인의 닉네임 |

            <br>
            <br>

            <b>Exception</b>:
            | Name | status_code  | Description |
            |-----|-----|-------|
            | LibraryNotFoundException | 404 | 서재 ID에 해당하는 서재를 찾을 수 없음 |
            | LibraryIsNotYoursException | 400 | 이 서재가 해당 닉네임 사용자에 해당하는 서재가 아님 |
            | LibraryBookNotFoundException | 404 | 조회하려는 서재 책 매퍼가 없음 |

            <br>
            <br>

            <b>Output</b>:
            <br>
                type: _description_

            | Var | Type | Description |
            |-----|-----|-------|
            |  |  |  |
            """
    )
    public ResponseEntity<ResponseLibraryBookDto> getBookOfLibrary(
        @PathVariable Long libraryId,
        @PathVariable Long bookId,
        // 다른사람인 경우도 해야되니까 nickname 으로 통일하기 그거에 대해 memberId로 변환하는 로직 하기
        @RequestParam String nickname
    ) {
        return ResponseEntity.ok()
                             .body(libraryService.getBookOfLibrary(libraryId, bookId, nickname));
    }

    /**
     * 서재 내 책을 수정합니다.
     *
     * @param headers   HTTP 헤더
     * @param libraryId 서재 ID
     * @param lbmDto    서재 책 매핑 수정 요청 DTO 리스트
     * @return 수정 성공 여부
     */
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

    /**
     * 사용자가 등록한 모든 책 개수를 조회합니다.
     *
     * @param headers HTTP 헤더
     * @return 사용자가 본인 서재에 등록한 모든 책 개수
     */
    @GetMapping("/me/books/count")
    @Operation(summary = "사용자가 등록한 모든 책 개수", description = "사용자가 본인 서재에 등록한 모든 책 개수")
    public ResponseEntity<Integer> countBooks(@RequestHeader HttpHeaders headers) {
        Long memberId = CommonUtil.getMemberId(headers);

        return ResponseEntity.ok()
                             .body(libraryService.countBooksInLibrary(memberId));
    }

    /**
     * 사용자가 등록한 책을 조회합니다.
     *
     * @param headers   HTTP 헤더
     * @param searchDto 책 검색 요청 DTO
     * @return 사용자가 본인 서재에 등록한 책 목록 응답 DTO 리스트
     */
    @PostMapping("/me/books/search")
    @Operation(
        summary = "사용자가 등록한 책 반환(프론트에서 서재 내 책검색 시 사용)",
        description = """
            사용자가 서재에 등록한 책 조회(필터링 포함)시 사용하는 API(conditions 를 빈 리스트로 보내면 조건 없는 필터링)


            <b>Input</b>:
            | Name | Type  | Description |
            |-----|-----|-------|
            | conditions | RequestSearchBookMultiFieldDto | 조건 데이터 구조 |
            | limit | int | 한 번의 요청에 나올 최대 데이터 개수 |
            | offset | int | 페이지 넘버 |

            <br>
            <br>

            <b>RequestSearchBookMultiFieldDto</b>:
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
    public ResponseEntity<List<ResponseBookDto>> getMyBooks(
        @RequestHeader HttpHeaders headers,
        @RequestBody RequestSearchBookMultiFieldDto searchDto
    ) {
        Long memberId = CommonUtil.getMemberId(headers);

        return ResponseEntity.ok()
                             .body(libraryService.getMyBooks(memberId, searchDto));
    }

    /**
     * 사용자 서재에 해당 책들이 등록되었는지 여부 판단
     *
     * @param memberId : 사용자 ID
     * @param bookIds  : 책 ID 리스트
     * @return Map<Long, Boolean>
     */
    @GetMapping("/books/check")
    @Operation(summary = "책 등록 여부 확인", description = "사용자의 서재에 여러 책이 등록되어 있는지 여부를 확인하는 API")
    public ResponseEntity<Map<Long, Boolean>> areBooksInLibrary(
        @RequestParam Long memberId,
        @RequestParam List<Long> bookIds
    ) {
        Map<Long, Boolean> booksInLibrary = libraryService.areBooksInLibrary(memberId, bookIds);
        return ResponseEntity.ok(booksInLibrary);
    }

    /**
     * 최근 서재 추가한 책 다섯개 반환
     *
     * @param headers 헤더
     * @return List<ResponseRecentFiveBookDto>
     */
    @GetMapping("/books/recent")
    @Operation(summary = "최근에 서재에 추가한 책 다섯개 반환", description = "사용자가 최근에 서재에 추가한 책 다섯개 반환하는 API")
    public ResponseEntity<List<ResponseRecentFiveBookDto>> getRecentFiveBooks(
        @RequestHeader HttpHeaders headers
    ) {
        Long memberId = CommonUtil.getMemberId(headers);
        return ResponseEntity.ok()
                             .body(libraryService.getMyRecentBooks(memberId));
    }

    @DeleteMapping
    @Operation(
        summary = "사용자 탈퇴시 사용할 API",
        description = """
            ### 사용자 탈퇴시 사용할 API
            - 서재 정보, 서재 북 매퍼 정보 사라짐

            ### status code : 204(No Content)

            """
    )
    public ResponseEntity<HttpStatus> deleteLibraryOfMember(
        @RequestParam("memberId") Long memberId
    ) {
        libraryService.deleteLibrariesByMemberId(memberId);

        return ResponseEntity.noContent()
                             .build();
    }
}
