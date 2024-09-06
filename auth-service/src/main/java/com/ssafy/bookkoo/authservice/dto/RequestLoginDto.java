package com.ssafy.bookkoo.authservice.dto;

import lombok.Builder;

@Builder
public record RequestLoginDto(
    String email,
    String password
) {

}
