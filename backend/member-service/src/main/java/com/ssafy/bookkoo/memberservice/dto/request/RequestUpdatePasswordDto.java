package com.ssafy.bookkoo.memberservice.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record RequestUpdatePasswordDto(
    @NotNull(message = "Member Id can not be null") String memberId,
    @Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$",
        message = "Invalid password."
    ) String password) {

}
