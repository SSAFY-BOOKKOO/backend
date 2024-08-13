package com.ssafy.bookkoo.booktalkservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record RequestCreateBookTalkDto(
    @NotNull Long bookId
) {

}

