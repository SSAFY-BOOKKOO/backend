package com.ssafy.bookkoo.libraryservice.service.stats;

import com.ssafy.bookkoo.libraryservice.dto.stats.ResponseStatsCategoryDto;
import com.ssafy.bookkoo.libraryservice.entity.Status;
import java.time.LocalDate;
import java.util.List;

public interface StatsService {

    /**
     * 독서 카테고리 통계 데이터 반환
     *
     * @param memberId 사용자 ID
     * @param startAt  시작 일
     * @param endAt    끝 일
     * @param status   독서 상태(Status 이넘)
     * @return ResponseStatsCategoryDto
     */
    List<ResponseStatsCategoryDto> getStatsCategories(
        Long memberId,
        LocalDate startAt,
        LocalDate endAt,
        Status status
    );

    /**
     * 시간 내에 읽은 권수
     *
     * @param memberId memberId
     * @param startAt  startAt
     * @param endAt    endAt
     * @return 읽은 권수
     */
    Integer getCountOfREAD(Long memberId, LocalDate startAt, LocalDate endAt);
}
