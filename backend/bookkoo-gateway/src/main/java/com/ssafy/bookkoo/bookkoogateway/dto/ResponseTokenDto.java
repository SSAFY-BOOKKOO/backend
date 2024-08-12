package com.ssafy.bookkoo.bookkoogateway.dto;


import lombok.Builder;

@Builder
public record ResponseTokenDto(
    String accessToken,
    String refreshToken
) {

}
