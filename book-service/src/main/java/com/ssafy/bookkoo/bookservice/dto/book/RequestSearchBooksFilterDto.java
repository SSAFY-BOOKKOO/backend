package com.ssafy.bookkoo.bookservice.dto.book;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.List;

@Schema(description = "책 검색 필터 DTO (곧 사라질 예정)")
public record RequestSearchBooksFilterDto(

    @Schema(description = "검색할 컬럼 이름", example = "title")
    @NotNull
    String field,

    @Schema(description = "검색할 값 리스트", example = "[\"Effective Java\", \"Java Concurrency in Practice\"]")
    @NotNull
    @Size(min = 1)
    List<String> value,

    @Schema(description = "한 페이지에 표시할 항목 수", example = "10")
    @Positive
    Integer limit,

    @Schema(description = "페이지 번호 (0부터 시작)", example = "0")
    @Positive
    Integer offset
) {

}
