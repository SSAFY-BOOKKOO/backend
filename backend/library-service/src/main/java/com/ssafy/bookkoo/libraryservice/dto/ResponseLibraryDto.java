package com.ssafy.bookkoo.libraryservice.dto;

import java.util.List;

public record ResponseLibraryDto(
    String name,
    String libraryOrder,
    LibraryStyleDto libraryStyleDto,
    List<ResponseBookDto> books
) {

}
