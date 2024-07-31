package com.ssafy.bookkoo.bookservice.dto.category;

import lombok.Builder;

@Builder
public record CategoryDto(
    Integer id,
    String name
) {

}
