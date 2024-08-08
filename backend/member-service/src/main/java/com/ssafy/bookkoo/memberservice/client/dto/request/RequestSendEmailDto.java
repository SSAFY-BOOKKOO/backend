package com.ssafy.bookkoo.memberservice.client.dto.request;

import lombok.Builder;

@Builder
public record RequestSendEmailDto(
    String subject,
    String content,
    String[] receivers
) {

}
