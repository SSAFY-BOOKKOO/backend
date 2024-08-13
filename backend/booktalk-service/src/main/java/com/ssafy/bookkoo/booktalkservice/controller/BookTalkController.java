package com.ssafy.bookkoo.booktalkservice.controller;


import com.ssafy.bookkoo.booktalkservice.dto.RequestCreateBookTalkDto;
import com.ssafy.bookkoo.booktalkservice.dto.ResponseBookDto;
import com.ssafy.bookkoo.booktalkservice.dto.ResponseBookTalkDto;
import com.ssafy.bookkoo.booktalkservice.dto.other.RequestSearchBookMultiFieldDto;
import com.ssafy.bookkoo.booktalkservice.service.BookTalkService;
import com.ssafy.bookkoo.booktalkservice.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/booktalks")
@RequiredArgsConstructor
public class BookTalkController {

    private final BookTalkService bookTalkService;


    /**
     * 독서록 메인에서 요청하는 인기있는 독서록 리스트를 반환하는 API
     *
     * @return 인기 있는 독서록 리스트 10개
     */
    @GetMapping
    @Operation(summary = "인기 있는 독서록 리스트",
        description = "오늘 하루 댓글이 가장 많은 10개의 독서록을 가져옵니다."
    )
    public ResponseEntity<List<ResponseBookTalkDto>> getBookTalk(
    ) {
        return ResponseEntity.ok(bookTalkService.getPopularBookTalkList(PageRequest.of(0, 10)));
    }


    /**
     * 새용자가 참가한 독서록 리스트를 반환하는 API
     *
     * @param headers 사용자 passport
     * @param order   정렬 순서 (time : 최근 채팅 순, chat : 채팅 많은 순)
     * @param page    페이징 번호
     * @return ResponseBookTalkDto
     */
    @Operation(summary = "내가 참여한 독서록 리스트",
        description = "내가 참여한 독서록 리스트를 가져옵니다. 페이징 : 10개씩, order : time -> 최근 채팅 순, chat -> 가장 많은 채팅 순 "
    )
    @GetMapping("/my")
    public ResponseEntity<List<ResponseBookTalkDto>> getMyBookTalk(
        @RequestHeader HttpHeaders headers,
        @RequestParam String order,
        @RequestParam Integer page
    ) {
        Long memberId = CommonUtil.getMemberId(headers);
        return ResponseEntity.ok(
            bookTalkService.getMyBookTalkList(memberId, order, PageRequest.of(page, 10)));
    }

    /**
     * 독서록을 생성하는 API
     *
     * @param dto (bookId : 독서록을 생성할 책 ID )
     * @return void
     */
    @Operation(summary = "독서록 생성",
        description = "책 번호로 독서록을 생성합니다."
    )
    @PostMapping
    public ResponseEntity<Long> createBookTalk(
        @Valid @RequestBody RequestCreateBookTalkDto dto
    ) {
        Long bookTalkId = bookTalkService.createBookTalk(dto);
        return ResponseEntity.ok(bookTalkId);
    }

    /**
     * 사용자를 독서록에 입장시키는 API
     * <p>
     * 자신이 참가한 독서록 리스트들을 보기 위함
     * <p>
     * 중복 참가시에는 무시됨
     *
     * @param headers    사용자 Passport
     * @param bookTalkId 독서록 ID
     * @return void
     */
    @Operation(summary = "독서록 참여",
        description = "독서록 번호로 독서록에 참여합니다."
    )
    @PostMapping("/enter/{bookTalkId}")
    public ResponseEntity<Void> enterBookTalk(
        @RequestHeader HttpHeaders headers,
        @PathVariable Long bookTalkId
    ) {
        Long memberId = CommonUtil.getMemberId(headers);
        bookTalkService.enterBookTalk(bookTalkId, memberId);
        return ResponseEntity.ok()
                             .build();
    }

    @Operation(summary = "책 번호로 독서록 조회",
        description = "책 번호로 독서록 조회"
    )
    @GetMapping("/book/{bookId}")
    public ResponseEntity<ResponseBookTalkDto> getBookTalkByBook(
        @PathVariable Long bookId
    ) {
        return ResponseEntity.ok(bookTalkService.getBookTalkByBookId(bookId));
    }

    /**
     * 북톡에 있는 책을 검색합니다.
     *
     * @param searchDto 책 검색 요청 DTO
     * @return 북톡에 있는 책 목록 응답 DTO 리스트
     */
    @PostMapping("/me/books/search")
    @Operation(
        summary = "북톡 모든 책 검색 반환(프론트에서 북톡 책검색 시 사용)",
        description = """
            북톡 모든 책 검색 반환(필터링 포함)시 사용하는 API(conditions 를 빈 리스트로 보내면 조건 없는 필터링)


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
    public ResponseEntity<List<ResponseBookDto>> searchBookTalkBooks(
        @RequestBody RequestSearchBookMultiFieldDto searchDto
    ) {
        return ResponseEntity.ok()
                             .body(bookTalkService.searchBookTalkBooks(searchDto));
    }
}
