package com.ssafy.bookkoo.authservice.dto;

import lombok.Builder;

@Builder
public record ResponseLoginTokenDto(
    String accessToken,
    String refreshToken
) {

}
