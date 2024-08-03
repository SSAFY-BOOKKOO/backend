package com.ssafy.bookkoo.notificationservice.client.dto;

public record ResponseMemberInfoDto(
    String memberId,
    String email,
    String nickName,
    String profileImgUrl,
    String introduction,
    Integer[] categories
) {

}
