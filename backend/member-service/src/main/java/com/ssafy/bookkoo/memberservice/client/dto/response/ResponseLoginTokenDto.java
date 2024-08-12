package com.ssafy.bookkoo.memberservice.client.dto.response;

import lombok.Builder;

@Builder
public record ResponseLoginTokenDto(
    String accessToken,
    String refreshToken
) {

}
