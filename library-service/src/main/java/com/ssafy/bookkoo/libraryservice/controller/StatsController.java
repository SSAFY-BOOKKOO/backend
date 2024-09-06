package com.ssafy.bookkoo.libraryservice.controller;

import com.ssafy.bookkoo.libraryservice.dto.stats.ResponseStatsCategoryDto;
import com.ssafy.bookkoo.libraryservice.entity.Status;
import com.ssafy.bookkoo.libraryservice.service.stats.StatsService;
import com.ssafy.bookkoo.libraryservice.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;

    /**
     * 카테고리 통계
     *
     * @param headers 사용자 ID를 가져올 헤더
     * @param startAt 시작 일
     * @param endAt   끝 일
     * @param status  상태
     * @return ResponseStatsCategoryDto
     */
    @GetMapping("/categories")
    @Operation(
        summary = "독서 카테고리 통계에 들어갈 데이터 반환",
        description = """
            ## 독서 카테고리 통계에 들어갈 데이터 반환


            ### Input:
            | Name | Type  | Description |
            |-----|-----|-------|
            | startAt | LocalDate | 시작일 |
            | endAt | LocalDate | 끝일 |
            | status | Status(READ, READING, DIB) | 상태. 안 줄경우 ALL 로 간주 |

            """
    )
    public ResponseEntity<List<ResponseStatsCategoryDto>> getStatsCategories(
        @RequestHeader HttpHeaders headers,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startAt,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endAt,
        @RequestParam(required = false) Status status
    ) {
        Long memberId = CommonUtil.getMemberId(headers);
        List<ResponseStatsCategoryDto> response = statsService.getStatsCategories(memberId, startAt,
            endAt, status);
        return ResponseEntity.ok()
                             .body(response);
    }


    /**
     * 시간 내에 읽은 권수
     *
     * @param headers 헤더
     * @param startAt startAt
     * @param endAt   endAt
     * @return 읽은 권수
     */
    @GetMapping("/read")
    @Operation(
        summary = "독서 권수 통계에 들어갈 데이터 반환",
        description = """
            ## 독서 읽은 권수 통계에 들어갈 데이터 반환


            ### Input:
            | Name | Type  | Description |
            |-----|-----|-------|
            | startAt | LocalDate | 시작일 |
            | endAt | LocalDate | 끝일 |

            """
    )
    public ResponseEntity<Integer> getCountOfREAD(
        @RequestHeader HttpHeaders headers,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startAt,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endAt
    ) {
        Long memberId = CommonUtil.getMemberId(headers);
        Integer response = statsService.getCountOfREAD(memberId, startAt, endAt);
        return ResponseEntity.ok()
                             .body(response);
    }
}