package com.ssafy.bookkoo.bookservice.dto.book;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * @param id
 * @param isbn
 */
@Builder
@Schema(description = "isbn을 토대로 해당 책이 DB 내에 존재하는지 여부")
public record CheckExistBookByIsbnDto(
    Long id,
    String isbn
) {

}
