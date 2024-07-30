package com.ssafy.bookkoo.libraryservice.dto;

import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.Builder;

@Builder
public record RequestSearchBookMultiFieldDto(
    // 검색 컬럼 이름
    List<SearchBookConditionDto> conditions,
    // 한 페이지에 몇개
    @Positive Integer limit,
    // 몇 페이지인지
    @Positive Integer offset
) {

}
