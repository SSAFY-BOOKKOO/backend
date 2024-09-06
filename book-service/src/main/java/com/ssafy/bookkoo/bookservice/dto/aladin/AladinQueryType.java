package com.ssafy.bookkoo.bookservice.dto.aladin;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 알라딘 API에서 사용할 검색 유형을 나타내는 열거형입니다.
 */
@Schema(description = "알라딘 API에서 사용할 검색 유형")
public enum AladinQueryType {

    @Schema(description = "제목으로 검색")
    Title,

    @Schema(description = "저자로 검색")
    Author,

    @Schema(description = "출판사로 검색")
    Publisher,

    @Schema(description = "키워드로 검색")
    Keyword
}
