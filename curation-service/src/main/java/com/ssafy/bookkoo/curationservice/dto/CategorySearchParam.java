package com.ssafy.bookkoo.curationservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;

@Builder
@Schema(description = "카테고리 검색 파라미터 DTO")
public record CategorySearchParam(

    @Schema(description = "검색할 필드 이름", example = "id or name")
    String field,

    @Schema(description = "검색할 값 리스트", example = "[\"1\", \"2\"]")
    List<String> values
) {

}
