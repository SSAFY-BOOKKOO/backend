package com.ssafy.bookkoo.bookservice.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "카테고리 DTO")
public record CategoryDto(

    @Schema(description = "카테고리 ID", example = "5")
    Integer id,

    @Schema(description = "카테고리 이름", example = "Programming")
    String name
) {

}
