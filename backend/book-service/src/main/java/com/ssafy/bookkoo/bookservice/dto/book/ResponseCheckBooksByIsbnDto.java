package com.ssafy.bookkoo.bookservice.dto.book;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "ISBN을 통한 책 존재 여부 응답 DTO")
public record ResponseCheckBooksByIsbnDto(

    @Schema(description = "책 ISBN", example = "978-0134685991")
    String isbn,

    @Schema(description = "책 존재 여부", example = "true")
    Boolean isInBook
) {

}
