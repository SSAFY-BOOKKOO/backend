package com.ssafy.bookkoo.bookservice.dto;

import lombok.Builder;

@Builder
public record RequestCreateBookDto(
    String coverImgUrl,
    String author,
    String publisher,
    String summary,
    String title,
    String isbn,
    Integer itemPage,
    Integer sizeDepth,
    Integer sizeHeight,
    Integer sizeWidth,
    Integer categoryId
) {

}
