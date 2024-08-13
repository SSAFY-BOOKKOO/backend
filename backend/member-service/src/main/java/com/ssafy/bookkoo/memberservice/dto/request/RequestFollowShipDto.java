package com.ssafy.bookkoo.memberservice.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RequestFollowShipDto(
    @NotBlank(message = "팔로워의 ID는 공백일 수 없습니다.")
    String memberId
) {

}
