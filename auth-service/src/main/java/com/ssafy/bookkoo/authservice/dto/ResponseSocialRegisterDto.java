package com.ssafy.bookkoo.authservice.dto;

import com.ssafy.bookkoo.authservice.entity.SocialType;
import lombok.Builder;

@Builder
public record ResponseSocialRegisterDto(
    String email,
    String nickName,
    SocialType socialType,
    String profileImgUrl
) {

}
