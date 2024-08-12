package com.ssafy.bookkoo.libraryservice.dto.stats;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "통계 속 책 데이터")
public class ResponseStatsBookDto {

    private Long id;

    private String title;

    private String coverImgUrl;
}
