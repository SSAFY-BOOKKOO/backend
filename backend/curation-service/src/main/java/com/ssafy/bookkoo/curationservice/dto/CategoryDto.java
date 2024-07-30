package com.ssafy.bookkoo.curationservice.dto;


import lombok.Builder;

@Builder
public record CategoryDto(
    Integer id,
    String name
) {

}
