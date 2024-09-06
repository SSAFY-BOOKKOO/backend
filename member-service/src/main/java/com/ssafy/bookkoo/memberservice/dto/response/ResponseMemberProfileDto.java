package com.ssafy.bookkoo.memberservice.dto.response;

import java.io.Serializable;
import lombok.Builder;

import java.util.List;

@Builder
public record ResponseMemberProfileDto(
    String memberId,
    String email,
    String nickName,
    String profileImgUrl,
    String introduction,
    Integer followerCnt,
    Integer followeeCnt,
    List<Integer> categories
) implements Serializable {

}
