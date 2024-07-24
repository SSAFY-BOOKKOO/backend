package com.ssafy.bookkoo.bookservice.dto;

import lombok.Builder;

@Builder
public record CategoryDto(
    Integer id,
    String name
) {

}
