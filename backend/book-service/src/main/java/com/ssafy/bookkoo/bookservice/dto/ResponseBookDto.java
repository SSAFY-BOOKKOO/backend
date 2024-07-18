package com.ssafy.bookkoo.bookservice.dto;

import lombok.Builder;

@Builder
public record ResponseBookDto(
    Long id,
    String author,
    String publisher,
    String summary,
    String title
) {

}
