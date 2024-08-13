package com.ssafy.bookkoo.memberservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record RequestUpdatePasswordDto(
    @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
    @Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$",
        message = "비밀번호 형식이 유효하지 않습니다."
    ) String password) {

}
