package com.ssafy.bookkoo.bookservice.dto;

import lombok.Builder;

@Builder
public record ResponseCheckBooksByIsbnDto(
    String isbn,
    Boolean isInBook
) {

}
