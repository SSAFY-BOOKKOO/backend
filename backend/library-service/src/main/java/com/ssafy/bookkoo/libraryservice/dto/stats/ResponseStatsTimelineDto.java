package com.ssafy.bookkoo.libraryservice.dto.stats;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Schema(description = "타임라인 상세 ")
public class ResponseStatsTimelineDto {

    @Schema(description = "활동한 시각")
    private LocalDate timestamp;

    @Schema(description = "어떤 이벤트 인지")
    private String event;

    @Schema(description = "책 정보")
    private ResponseStatsBookDto book;

    @Builder
    @QueryProjection
    public ResponseStatsTimelineDto(
        LocalDate timestamp,
        String event,
        ResponseStatsBookDto book
    ) {
        this.timestamp = timestamp;
        this.event = event;
        this.book = book;
    }
}
