package com.ssafy.bookkoo.libraryservice.dto.other;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Builder;

@Builder
@Schema(description = "책 응답 DTO")
public record ResponseBookDto(

    @Schema(description = "책 ID", example = "1")
    Long id,

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

    @Schema(description = "ISBN", example = "978-0134685991")
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

    @Schema(description = "카테고리 정보")
    CategoryDto category,

    Long libraryId
) {

    public ResponseBookDto withLibraryId(Long libraryId) {
        return new ResponseBookDto(
            this.id,
            this.coverImgUrl,
            this.author,
            this.publisher,
            this.summary,
            this.title,
            this.isbn,
            this.itemPage,
            this.sizeDepth,
            this.sizeHeight,
            this.sizeWidth,
            this.publishedAt,
            this.category,
            libraryId
        );
    }
}
