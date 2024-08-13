package com.ssafy.bookkoo.libraryservice.dto.library;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

/**
 * 서재 생성 요청 정보를 담는 DTO 클래스입니다.
 */
@Builder
@Schema(description = "서재 생성 요청 정보를 담는 DTO")
public record RequestCreateLibraryDto(
    @NotBlank
    @Schema(description = "서재 이름", example = "My Library")
    String name,

    @Min(value = 1)
    @Max(value = 3)
    @Schema(description = "서재 순서", example = "1")
    Integer libraryOrder,

    @Schema(description = "서재 스타일 정보")
    LibraryStyleDto libraryStyleDto

) {

}
