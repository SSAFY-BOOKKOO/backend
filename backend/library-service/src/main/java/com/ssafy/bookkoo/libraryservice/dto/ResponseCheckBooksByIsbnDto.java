package com.ssafy.bookkoo.libraryservice.dto;

import lombok.Builder;

@Builder
public record ResponseCheckBooksByIsbnDto(
    String isbn,
    Boolean isInBook
) {

}
