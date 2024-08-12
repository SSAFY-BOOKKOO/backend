package com.ssafy.bookkoo.memberservice.dto.request;


import lombok.Builder;

@Builder
public record RequestCertificationDto(
    String email,
    String certNum) {

}
