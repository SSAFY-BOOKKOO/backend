package com.ssafy.bookkoo.libraryservice.service.stats;

import com.ssafy.bookkoo.libraryservice.client.BookServiceClient;
import com.ssafy.bookkoo.libraryservice.dto.stats.ResponseCalendarDto;
import com.ssafy.bookkoo.libraryservice.dto.stats.ResponseStatsCategoryDto;
import com.ssafy.bookkoo.libraryservice.dto.stats.ResponseStatsTimelineDto;
import com.ssafy.bookkoo.libraryservice.entity.Status;
import com.ssafy.bookkoo.libraryservice.exception.BookClientException;
import com.ssafy.bookkoo.libraryservice.repository.LibraryBookMapperRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final LibraryBookMapperRepository libraryBookMapperRepository;
    private final BookServiceClient bookServiceClient;

    /**
     * 독서 달력에 들어갈 데이터 반환
     *
     * @param memberId 사용자 ID
     * @param startAt  시작 일
     * @param endAt    끝 일
     * @return ResponseCalendarDto
     */
    @Override
    public ResponseCalendarDto getCalendar(Long memberId, LocalDate startAt, LocalDate endAt) {
        return null;
    }

    /**
     * 독서 카테고리 통계 데이터 반환
     *
     * @param memberId 사용자 ID
     * @param startAt  시작 일
     * @param endAt    끝 일
     * @param status   독서 상태(Status 이넘)
     * @return ResponseStatsCategoryDto
     */
    @Override
    public List<ResponseStatsCategoryDto> getStatsCategories(
        Long memberId,
        LocalDate startAt,
        LocalDate endAt,
        Status status
    ) {
        // 1. 멤버가 서재에 넣은 책 중 READ 이면서 읽은 기간이 파라미터 범위에 해당되는 책의 ID 가져오기
        List<Long> bookIds = libraryBookMapperRepository.findBookIdsByStatsCategoriesCondition(
            memberId, startAt, endAt, status);

        // 2. 해당 bookIds 에 대해 카테고리 통계를 받아오기
        List<ResponseStatsCategoryDto> categoryDtos;
        try {
            categoryDtos = bookServiceClient.getBookCategoryStats(bookIds);
        } catch (Exception e) {
            throw new BookClientException(e.getMessage());
        }
        categoryDtos.sort(null);
        return categoryDtos;
    }


    /**
     * 독서 타임라인 데이터 반환
     *
     * @param memberId 사용자 ID
     * @param startAt  시작 일
     * @param endAt    끝 일
     * @return List<ResponseStatsTimelineDto>
     */
    @Override
    public List<ResponseStatsTimelineDto> getStatsTimeline(
        Long memberId,
        LocalDate startAt,
        LocalDate endAt
    ) {
        // 1. 멤버가 서재에 넣은 책 가져오기
        List<Long> bookIds = libraryBookMapperRepository.findBookIdsByMemberIdUpdatedAt(
            memberId, startAt, endAt);
        return List.of();
    }
}
