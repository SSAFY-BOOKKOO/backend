package com.ssafy.bookkoo.libraryservice.dto.stats;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "통계 카테고리 DTO")
public record StatsCategoryDto(
    @Schema(description = "카테고리 이름")
    String name,
    @Schema(description = "권수")
    Integer count
) {

}
