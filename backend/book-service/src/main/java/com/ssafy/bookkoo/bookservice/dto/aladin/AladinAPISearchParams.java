package com.ssafy.bookkoo.bookservice.dto.aladin;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;

/**
 * Aladin 검색 API를 사용할 때 필요한 파라미터를 담는 DTO 클래스입니다.
 */
@Getter
@Builder
public class AladinAPISearchParams {

    /**
     * 검색어
     */
    private String query;

    /**
     * 검색 유형 (기본값: Title)
     */
    @Builder.Default
    private AladinQueryType queryType = AladinQueryType.Title;

    /**
     * 최대 검색 결과 수 (기본값: 10, 최소값: 1, 최대값: 100)
     */
    @Min(value = 1, message = "maxResult는 최소 1이어야 합니다")
    @Max(value = 100, message = "maxResult는 최대 100이어야 합니다")
    @Builder.Default
    private Integer maxResult = 10;

    /**
     * 검색 시작 위치 (기본값: 1, 최소값: 1)
     */
    @Min(value = 1, message = "start는 최소 1이어야 합니다")
    @Builder.Default
    private Integer start = 1;
}
