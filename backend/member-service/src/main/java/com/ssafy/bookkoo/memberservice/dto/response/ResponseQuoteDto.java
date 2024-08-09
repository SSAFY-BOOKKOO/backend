package com.ssafy.bookkoo.memberservice.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ResponseQuoteDto(
    String source,
    String content,
    LocalDateTime createdAt
) {

}
