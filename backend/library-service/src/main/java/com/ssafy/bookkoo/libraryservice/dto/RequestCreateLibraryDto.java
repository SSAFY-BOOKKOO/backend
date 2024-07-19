package com.ssafy.bookkoo.libraryservice.dto;

public record RequestCreateLibraryDto(
    String name,
    String libraryOrder,
    LibraryStyleDto libraryStyleDto
) {

}
