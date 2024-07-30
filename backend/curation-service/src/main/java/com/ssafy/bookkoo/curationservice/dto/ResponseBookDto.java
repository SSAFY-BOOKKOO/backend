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
    String isbn,
    Integer itemPage,
    Integer sizeDepth,
    Integer sizeHeight,
    Integer sizeWidth,
    CategoryDto category
) {

}
