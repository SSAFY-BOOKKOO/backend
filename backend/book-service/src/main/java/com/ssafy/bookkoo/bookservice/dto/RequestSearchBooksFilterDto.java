package com.ssafy.bookkoo.bookservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.List;

// 곧 사라질 예정
public record RequestSearchBooksFilterDto(
    // 검색 컬럼 이름
    @NotNull String field,
    // 컬럼의 값
    @NotNull @Size(min = 1) List<String> value,
    // 순서 어떻게 할지
//    String orderBy,
    // 한 페이지에 몇개
    @Positive Integer limit,
    // 몇 페이지인지
    @Positive Integer offset
) {

}
