package com.ssafy.bookkoo.libraryservice.dto;

public record RequestUpdateLibraryDto(
    String name,
    String libraryOrder,
    LibraryStyleDto libraryStyleDto
) {

}
