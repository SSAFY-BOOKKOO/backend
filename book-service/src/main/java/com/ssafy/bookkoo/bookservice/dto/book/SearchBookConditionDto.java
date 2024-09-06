package com.ssafy.bookkoo.bookservice.dto.book;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Builder;

@Builder
@Schema(description = "책 검색 조건 DTO")
public record SearchBookConditionDto(

    @Schema(description = "검색할 컬럼 이름", example = "title")
    @NotNull
    String field,

    @Schema(description = "검색할 값 리스트", example = "[\"Effective Java\", \"Java Concurrency in Practice\"]")
    @NotNull
    @Size(min = 1)
    List<String> values
) {

}
