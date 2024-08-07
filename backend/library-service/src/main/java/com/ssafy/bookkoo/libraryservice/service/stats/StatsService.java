package com.ssafy.bookkoo.libraryservice.service.stats;

import com.ssafy.bookkoo.libraryservice.dto.stats.ResponseCalendarDto;
import com.ssafy.bookkoo.libraryservice.dto.stats.ResponseStatsCategoryDto;
import com.ssafy.bookkoo.libraryservice.entity.Status;
import java.time.LocalDate;

public interface StatsService {

    /**
     * 독서 달력에 들어갈 데이터 반환
     *
     * @param memberId 사용자 ID
     * @param startAt  시작 일
     * @param endAt    끝 일
     * @return ResponseCalendarDto
     */
    ResponseCalendarDto getCalendar(Long memberId, LocalDate startAt, LocalDate endAt);

    /**
     * 독서 카테고리 통계 데이터 반환
     *
     * @param memberId 사용자 ID
     * @param startAt  시작 일
     * @param endAt    끝 일
     * @param status   독서 상태(Status 이넘)
     * @return ResponseStatsCategoryDto
     */
    ResponseStatsCategoryDto getStatsCategories(
        Long memberId,
        LocalDate startAt,
        LocalDate endAt,
        Status status
    );
}
