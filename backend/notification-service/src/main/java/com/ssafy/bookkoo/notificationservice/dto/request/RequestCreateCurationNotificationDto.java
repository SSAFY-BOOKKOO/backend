package com.ssafy.bookkoo.notificationservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
@Builder
public record RequestCreateCurationNotificationDto(
    @NotNull(message = "Member ID can not be null")
    Long[] memberIds,
    @NotNull(message = "Curation ID can not be null")
    Long curationId,
    @NotNull(message = "Writer Id can not be null")
    Long writerId
) {

}