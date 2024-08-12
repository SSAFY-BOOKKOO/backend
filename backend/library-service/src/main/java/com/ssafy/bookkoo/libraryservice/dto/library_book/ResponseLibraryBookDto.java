package com.ssafy.bookkoo.libraryservice.dto.library_book;

import com.ssafy.bookkoo.libraryservice.dto.other.ResponseBookOfLibraryDto;
import com.ssafy.bookkoo.libraryservice.entity.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record ResponseLibraryBookDto(
    @Schema(description = "책 응답 정보")
    ResponseBookOfLibraryDto book,

    @Schema(description = "책 순서", example = "1")
    Integer bookOrder,

    @Schema(description = "책 색상", example = "#FFFFFF")
    String bookColor,

    @Schema(description = "시작일", example = "2024-01-01")
    LocalDate startAt,

    @Schema(description = "종료일", example = "2024-12-31")
    LocalDate endAt,

    @Schema(description = "상태", example = "READING")
    Status status,

    @Schema(description = "평점", example = "5")
    Integer rating

) {

}
