package com.ssafy.bookkoo.booktalkservice.dto.other;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.Builder;

@Builder
@Schema(description = "책 검색 요청 DTO")
public record RequestSearchBookMultiFieldDto(

    @Schema(description = "검색 조건 리스트", example = "[{\"field\": \"title\", \"values\": [\"Effective Java\"]}, {\"field\": \"author\", \"value\": \"Joshua Bloch\"}]")
    List<SearchBookConditionDto> conditions,

    @Schema(description = "한 페이지에 표시할 항목 수", example = "10")
    @Positive
    Integer limit,

    @Schema(description = "페이지 번호 (0부터 시작)", example = "0")
    @Positive
    Integer offset
) {

}
