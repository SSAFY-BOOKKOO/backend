package com.ssafy.bookkoo.commonservice.email.dto.request;

import lombok.Builder;

@Builder
public record RequestSendEmailDto(
    String subject,
    String content,
    String[] receivers
) {

}
