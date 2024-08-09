package com.ssafy.bookkoo.libraryservice.dto.stats;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Schema(description = "통계 카테고리 DTO")
public class ResponseStatsCategoryDto implements Comparable<ResponseStatsCategoryDto> {

    @Schema(description = "카테고리 이름")
    private String name;
    @Schema(description = "권수")
    private Integer count;

    @Builder
    @QueryProjection
    public ResponseStatsCategoryDto(String name, Integer count) {
        this.name = name;
        this.count = count;
    }

    @Override
    public int compareTo(ResponseStatsCategoryDto o) {
        return Integer.compare(o.count, this.count);
    }
}
