package com.ssafy.bookkoo.curationservice.dto;

import lombok.Builder;

@Builder
public record RequestSendEmailDto(
    String subject,
    String content,
    String[] receivers
) {

}
