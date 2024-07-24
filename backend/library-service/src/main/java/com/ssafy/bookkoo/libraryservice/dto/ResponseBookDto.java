package com.ssafy.bookkoo.libraryservice.dto;

public record ResponseBookDto(
    Long id,
    String title,
    String coverImgUrl,
    String author,
    String publisher,
    String summary,
    String isbn,
    CategoryDto category
) {

}
