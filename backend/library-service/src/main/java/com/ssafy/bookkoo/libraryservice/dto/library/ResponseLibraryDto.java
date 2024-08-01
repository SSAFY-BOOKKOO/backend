package com.ssafy.bookkoo.libraryservice.dto.library;

import com.ssafy.bookkoo.libraryservice.dto.library_book.ResponseLibraryBookMapperDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;

/**
 * 서재 응답 정보를 담는 DTO 클래스입니다.
 */
@Schema(description = "서재 응답 정보를 담는 DTO")
@Builder
public record ResponseLibraryDto(

    @Schema(description = "서재 ID", example = "1")
    Long id,

    @Schema(description = "서재 이름", example = "My Library")
    String name,

    @Schema(description = "서재 순서", example = "1")
    String libraryOrder,

    @Schema(description = "서재 스타일 정보")
    LibraryStyleDto libraryStyleDto,

    @Schema(description = "서재에 포함된 책 리스트")
    List<ResponseLibraryBookMapperDto> books,

    @Schema(description = "서재에 포함된 책 개수", example = "5")
    Integer bookCount
) {

}
