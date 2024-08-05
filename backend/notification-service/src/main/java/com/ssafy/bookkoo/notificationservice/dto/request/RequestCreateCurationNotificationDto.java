package com.ssafy.bookkoo.notificationservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
@Builder
public record RequestCreateCurationNotificationDto(
    @NotNull(message = "Member ID can not be null")
    String memberId,
    @NotNull(message = "Curation ID can not be null")
    Long curationId
) {

}