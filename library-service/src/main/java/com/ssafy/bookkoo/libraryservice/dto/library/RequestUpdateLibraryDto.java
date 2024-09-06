package com.ssafy.bookkoo.libraryservice.dto.library;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 서재 수정 요청 정보를 담는 DTO 클래스입니다.
 */
@Schema(description = "서재 수정 요청 정보를 담는 DTO")
public record RequestUpdateLibraryDto(

    @Schema(description = "서재 이름", example = "Updated Library Name")
    String name,

    @Schema(description = "서재 순서", example = "1")
    Integer libraryOrder,

    @Schema(description = "서재 스타일 정보")
    LibraryStyleDto libraryStyleDto

) {

}
