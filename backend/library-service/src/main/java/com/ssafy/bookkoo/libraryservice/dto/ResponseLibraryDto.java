package com.ssafy.bookkoo.libraryservice.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record ResponseLibraryDto(
    String name,
    String libraryOrder,
    LibraryStyleDto libraryStyleDto,
    List<ResponseLibraryBookMapperDto> books
) {

}
