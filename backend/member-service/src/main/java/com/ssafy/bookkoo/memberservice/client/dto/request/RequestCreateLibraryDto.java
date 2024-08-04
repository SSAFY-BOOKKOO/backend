package com.ssafy.bookkoo.memberservice.client.dto.request;

import lombok.Builder;

@Builder
public record RequestCreateLibraryDto (
    String name,
    Integer libraryOrder,
    LibraryStyleDto libraryStyleDto
) {
}
