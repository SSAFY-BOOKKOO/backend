package com.ssafy.bookkoo.memberservice.client.dto.request;

import lombok.Builder;

@Builder
public record RequestLoginDto(
    String email,
    String password
) {

}
