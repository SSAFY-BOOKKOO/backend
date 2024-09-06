package com.ssafy.bookkoo.memberservice.repository.custom;

import com.ssafy.bookkoo.memberservice.dto.response.ResponseRecipientDto;
import com.ssafy.bookkoo.memberservice.entity.MemberInfo;

import java.util.List;

public interface MemberInfoCustomRepository {

    List<Long> findRandomMemberInfoIdByFollowers(List<Long> followers);

    List<ResponseRecipientDto> findByRecipientsInfoByIds(List<Long> recipientIds);

    List<MemberInfo> findTOP10ByNickNameContainsAndIdNe(Long id, String nickName);
}