package com.ssafy.bookkoo.memberservice.dto.request;

import com.ssafy.bookkoo.memberservice.annotation.MaxArray;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record RequestUpdateMemberInfoDto(
    @NotNull(message = "nickName can not be null") String nickName,
    @MaxArray(minValue = 1, maxValue = 15) Integer[] categories,
    String introduction
) {

}
