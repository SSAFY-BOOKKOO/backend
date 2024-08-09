package com.ssafy.bookkoo.libraryservice.dto.stats;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;

@Builder
@Schema(description = "독서 달력 DTO")
public record ResponseCalendarDto(
    @Schema(description = "날짜")
    LocalDate date,
    @Schema(description = "책 권수")
    Integer bookCount,
    @Schema(description = "책 정보")
    List<ResponseCalendarBookDto> books
) {

}
