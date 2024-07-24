package com.ssafy.bookkoo.libraryservice.dto;

public record RequestCreateLibraryDto(
    String name,
    Integer libraryOrder,
    LibraryStyleDto libraryStyleDto
) {

}
