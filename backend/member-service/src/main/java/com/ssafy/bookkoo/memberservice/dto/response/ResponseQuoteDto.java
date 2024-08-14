package com.ssafy.bookkoo.memberservice.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ResponseQuoteDto(
    Long quoteId,
    String source,
    String content,
    String fontName,
    String fontColor,
    Integer fontSize,
    LocalDateTime createdAt
) {

}
