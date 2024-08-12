package com.ssafy.bookkoo.memberservice.dto.request;

import com.ssafy.bookkoo.memberservice.annotation.MaxArray;
import com.ssafy.bookkoo.memberservice.enums.Gender;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record RequestAdditionalInfo(
    @NotNull(message = "id can not be null") Long id,
    @NotNull(message = "memberId can not be null") String memberId,
    @NotNull(message = "nickName can not be null") String nickName,
    Integer year,
    Gender gender,
    @MaxArray(minValue = 1, maxValue = 15) Integer[] categories,
    String introduction,
    String profileImgUrl) {

}
