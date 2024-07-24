package com.ssafy.bookkoo.libraryservice.dto;

public record RequestUpdateLibraryDto(
    String name,
    Integer libraryOrder,
    LibraryStyleDto libraryStyleDto
) {

}
