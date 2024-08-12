package com.ssafy.bookkoo.memberservice.repository.custom;

import com.ssafy.bookkoo.memberservice.dto.response.ResponseRecipientDto;
import java.util.List;

public interface MemberInfoCustomRepository {

    List<Long> findRandomMemberInfoIdByFollowers(List<Long> followers);

    List<ResponseRecipientDto> findByRecipientsInfoByIds(List<Long> recipientIds);
}