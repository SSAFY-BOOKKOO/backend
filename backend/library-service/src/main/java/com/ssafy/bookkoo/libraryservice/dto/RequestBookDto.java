package com.ssafy.bookkoo.libraryservice.dto;

public record RequestBookDto(
    String title,
    String coverImgUrl,
    String author,
    String publisher,
    String summary,
    String isbn,
    String category
) {

}
