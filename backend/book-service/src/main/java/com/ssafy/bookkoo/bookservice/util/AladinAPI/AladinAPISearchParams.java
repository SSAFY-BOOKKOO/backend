package com.ssafy.bookkoo.bookservice.util.AladinAPI;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;

/**
 * Aladin 검색 API 사용할 때 쓰는 파라미터
 */
@Getter
@Builder
public class AladinAPISearchParams {

    private String query;

    @Builder.Default
    private AladinQueryType queryType = AladinQueryType.Title;

    @Min(value = 1, message = "maxResult must be at least 1")
    @Max(value = 100, message = "maxResult must be at most 100")
    @Builder.Default
    private Integer maxResult = 10;

    @Min(value = 1, message = "maxResult must be at least 1")
    @Builder.Default
    private Integer start = 1;
}
