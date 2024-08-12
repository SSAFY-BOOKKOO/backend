package com.ssafy.bookkoo.curationservice.dto;

import lombok.Builder;

@Builder
public record RequestCreateCurationNotificationDto(
    Long[] memberIds,
    Long curationId,
    Long writerId
) {

}