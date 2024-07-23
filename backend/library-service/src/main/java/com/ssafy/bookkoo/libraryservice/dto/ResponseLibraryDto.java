package com.ssafy.bookkoo.libraryservice.dto;

import java.util.List;
import lombok.Setter;

public record ResponseLibraryDto(
    String name,
    String libraryOrder,
    LibraryStyleDto libraryStyleDto,
    @Setter
    List<ResponseBookDto> books
) {

}
