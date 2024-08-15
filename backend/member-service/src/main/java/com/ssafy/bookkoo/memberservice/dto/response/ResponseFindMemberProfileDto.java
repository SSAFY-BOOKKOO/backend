package com.ssafy.bookkoo.memberservice.dto.response;

import lombok.Builder;

import java.io.Serializable;
import java.util.List;

@Builder
public record ResponseFindMemberProfileDto(
    String memberId,
    String email,
    String nickName,
    String profileImgUrl,
    String introduction,
    Boolean isFollow,
    Integer followerCnt,
    Integer followeeCnt,
    List<Integer> categories
) implements Serializable {

}
