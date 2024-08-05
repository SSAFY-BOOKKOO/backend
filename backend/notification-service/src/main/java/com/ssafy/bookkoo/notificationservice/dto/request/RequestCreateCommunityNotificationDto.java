package com.ssafy.bookkoo.notificationservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
public record RequestCreateCommunityNotificationDto(
    @NotNull(message = "Member ID can not be null")
    String memberId,
    @NotNull(message = "Community ID can not be null")
    Long communityId
) {

}