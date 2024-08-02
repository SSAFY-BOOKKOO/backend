package com.ssafy.bookkoo.libraryservice.dto.library_book;

import com.ssafy.bookkoo.libraryservice.dto.other.RequestCreateBookDto;
import com.ssafy.bookkoo.libraryservice.entity.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.sql.Date;

/**
 * 서재 책 매핑 생성 요청 정보를 담는 DTO 클래스입니다.
 */
@Schema(description = "서재 책 매핑 생성 요청 정보를 담는 DTO")
public record RequestLibraryBookMapperCreateDto(

    @Schema(description = "책 색상", example = "#FFFFFF")
    String bookColor,

    @Schema(description = "시작일", example = "2024-01-01")
    Date startAt,

    @Schema(description = "종료일", example = "2024-12-31")
    Date endAt,

    @Schema(description = "상태", example = "READING")
    Status status,

    @Min(value = 1)
    @Max(value = 5)
    @Schema(description = "평점", example = "5")
    Integer rating,

    @Schema(description = "책 생성 요청 정보")
    RequestCreateBookDto bookDto

) {

}
