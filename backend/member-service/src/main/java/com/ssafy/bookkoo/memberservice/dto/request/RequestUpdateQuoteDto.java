package com.ssafy.bookkoo.memberservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record RequestUpdateQuoteDto(
    @NotNull
    Long quoteId,
    @NotNull
    String content,
    @NotNull
    String source
) {

}
