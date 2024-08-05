package com.ssafy.bookkoo.notificationservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record RequestCreateFollowNotificationDto(
    @NotNull(message = "Member ID can not be null") String memberId,
    @NotNull(message = "Follower ID can not be null") String followerId
) {

}
