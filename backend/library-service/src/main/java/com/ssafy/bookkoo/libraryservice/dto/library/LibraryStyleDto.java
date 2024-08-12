package com.ssafy.bookkoo.libraryservice.dto.library;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * 서재 스타일 정보를 담는 DTO 클래스입니다.
 */
@Builder
@Schema(description = "서재 스타일 정보를 담는 DTO")
public record LibraryStyleDto(

    @Schema(description = "서재 색상", example = "#FFFFFF")
    String libraryColor,

    @Schema(description = "서재 폰트 이름", example = "쿠키런!")
    String fontName,

    @Schema(description = "서재 폰트 크기", example = "3")
    String fontSize

) {

}
