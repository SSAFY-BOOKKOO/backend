package com.ssafy.bookkoo.bookservice.dto.book;

import lombok.Builder;

@Builder
public record ResponseCheckBooksByIsbnDto(
    String isbn,
    Boolean isInBook
) {

}
