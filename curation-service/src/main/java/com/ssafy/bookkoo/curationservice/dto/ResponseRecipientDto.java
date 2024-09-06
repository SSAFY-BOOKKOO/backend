package com.ssafy.bookkoo.curationservice.dto;


public record ResponseRecipientDto(
    Long memberId,
    String email,
    Boolean isReceiveEmail) {

}
