package com.ssafy.bookkoo.memberservice.dto;


import lombok.Builder;

@Builder
public record RequestCertificationDto(
    String email,
    String certNum
) {

}
