package com.ssafy.bookkoo.memberservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record RequestCreateFollowNotificationDto(
    @NotBlank(message = "멤버 ID는 공백일 수 없습니다.")
    Long memberId,
    @NotBlank(message = "팔로워의 ID는 공백일 수 없습니다.")
    Long followerId
) {

}
