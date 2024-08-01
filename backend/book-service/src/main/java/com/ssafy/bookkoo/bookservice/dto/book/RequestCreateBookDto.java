package com.ssafy.bookkoo.bookservice.dto.book;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "책 생성 요청 DTO")
public record RequestCreateBookDto(

    @Schema(description = "책 표지 이미지 URL", example = "http://example.com/cover.jpg")
    String coverImgUrl,

    @Schema(description = "책 저자", example = "Joshua Bloch")
    String author,

    @Schema(description = "출판사", example = "Addison-Wesley")
    String publisher,

    @Schema(description = "책 요약", example = "Effective Java is a programming book by Joshua Bloch ...")
    String summary,

    @Schema(description = "책 제목", example = "Effective Java")
    String title,

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

    @Schema(description = "카테고리 ID", example = "5")
    Integer categoryId
) {

}