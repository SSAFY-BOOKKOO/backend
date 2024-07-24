package com.ssafy.bookkoo.curationservice.dto;

import lombok.Builder;

@Builder
public record ResponseBookDto(
    Long id,
    String coverImgUrl,
    String author,
    String publisher,
    String summary,
    String title,
    String isbn
) {

}