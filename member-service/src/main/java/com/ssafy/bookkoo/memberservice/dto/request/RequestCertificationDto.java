package com.ssafy.bookkoo.memberservice.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record RequestCertificationDto(
    @NotBlank(message = "이메일은 공백일 수 없습니다.")
    @Pattern(
        regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
        message = "이메일 형식이 올바르지 않습니다."
    ) String email,
    @NotBlank(message = "인증번호는 공백일 수 없습니다.")
    String certNum) {

}
