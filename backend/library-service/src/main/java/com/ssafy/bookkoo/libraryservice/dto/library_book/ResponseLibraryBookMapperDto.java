package com.ssafy.bookkoo.libraryservice.dto.library_book;

import com.ssafy.bookkoo.libraryservice.dto.other.ResponseBookDto;
import com.ssafy.bookkoo.libraryservice.entity.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Builder;

/**
 * 서재 책 매핑 응답 정보를 담는 DTO 클래스입니다.
 */
@Schema(description = "서재 책 매핑 응답 정보를 담는 DTO")
@Builder
public record ResponseLibraryBookMapperDto(

    @Schema(description = "책 응답 정보")
    ResponseBookDto book,

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
