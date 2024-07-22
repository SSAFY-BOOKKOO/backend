package com.ssafy.bookkoo.memberservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record RequestAdditionalInfo(
    @NotNull(message = "memberId can not be null") String memberId,
    @NotNull(message = "nickName can not be null") String nickName,
    Integer year,
    String gender,
    String[] categories,
    String introduction) {

}
