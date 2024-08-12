package com.ssafy.bookkoo.memberservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record RequestCreateQuoteDto(
    @NotNull
    String source,
    @NotNull
    String content
) {

}
