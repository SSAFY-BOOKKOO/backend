package com.ssafy.bookkoo.bookservice.dto.book;

import com.ssafy.bookkoo.bookservice.dto.category.CategoryDto;
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
