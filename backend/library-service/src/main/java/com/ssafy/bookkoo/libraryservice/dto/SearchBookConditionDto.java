package com.ssafy.bookkoo.libraryservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Builder;

@Builder
public record SearchBookConditionDto(
    // 컬럼 이름
    @NotNull String field,
    // 값
    @NotNull @Size(min = 1) List<String> values
) {

}
