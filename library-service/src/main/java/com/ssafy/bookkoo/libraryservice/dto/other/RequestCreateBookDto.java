package com.ssafy.bookkoo.libraryservice.dto.other;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import lombok.Builder;

@Builder
@Schema(description = "책 생성 요청 DTO")
public record RequestCreateBookDto(

    @Schema(description = "책 표지 이미지 URL", example = "http://example.com/cover.jpg")
    String coverImgUrl,

    @NotBlank
    @Schema(description = "책 저자", example = "Joshua Bloch")
    String author,

    @NotBlank
    @Schema(description = "출판사", example = "Addison-Wesley")
    String publisher,

    @Schema(description = "책 요약", example = "Effective Java is a programming book by Joshua Bloch ...")
    String summary,

    @NotBlank
    @Schema(description = "책 제목", example = "Effective Java")
    String title,

    @NotBlank
    @Schema(description = "ISBN", example = "0134685991")
    String isbn,

    @Schema(description = "책 페이지 수", example = "412")
    Integer itemPage,

    @Schema(description = "책 두께(mm)", example = "20")
    Integer sizeDepth,

    @Schema(description = "책 높이(mm)", example = "230")
    Integer sizeHeight,

    @Schema(description = "책 너비(mm)", example = "150")
    Integer sizeWidth,

    @Schema(description = "출판일", example = "2023-01-10")
    LocalDate publishedAt,

    @Schema(description = "카테고리")
    CategoryDto category
) {

}
