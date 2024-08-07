package com.ssafy.bookkoo.libraryservice.dto.stats;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;

@Builder
@Schema(description = "통계 카테고리 반환 값")
public record ResponseStatsCategoryDto(
    List<StatsCategoryDto> categories
) {

}
