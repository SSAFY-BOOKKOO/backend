package com.ssafy.bookkoo.libraryservice.dto.other;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "최근 서재에 추가한 책 다섯권 조회할 때의 DTO")
public record ResponseRecentFiveBookDto(
    
    @Schema(description = "책 제목")
    String title,
    @Schema(description = "책 저자")
    String author
) {

}
