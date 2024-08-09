package com.ssafy.bookkoo.libraryservice.dto.stats;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "독서 달력에 들어갈 책 정보")
public record ResponseCalendarBookDto(
    @Schema(description = "책 ID")
    Long id,
    @Schema(description = "책 커버 이미지 URL")
    String coverImgUrl,
    @Schema(description = "책 제목")
    String title
) {

}
