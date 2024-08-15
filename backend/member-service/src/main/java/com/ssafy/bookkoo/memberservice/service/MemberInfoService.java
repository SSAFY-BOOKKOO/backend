package com.ssafy.bookkoo.memberservice.service;

import com.ssafy.bookkoo.memberservice.dto.request.RequestMemberSettingDto;
import com.ssafy.bookkoo.memberservice.dto.request.RequestUpdateMemberInfoDto;
import com.ssafy.bookkoo.memberservice.dto.request.RequestUpdatePasswordDto;
import com.ssafy.bookkoo.memberservice.dto.response.ResponseFindMemberProfileDto;
import com.ssafy.bookkoo.memberservice.dto.response.ResponseMemberInfoDto;
import com.ssafy.bookkoo.memberservice.dto.response.ResponseMemberProfileDto;
import com.ssafy.bookkoo.memberservice.dto.response.ResponseRecipientDto;
import com.ssafy.bookkoo.memberservice.entity.MemberInfo;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface MemberInfoService {

    void updatePassword(Long id, RequestUpdatePasswordDto requestUpdatePasswordDto);

    ResponseMemberProfileDto getMemberProfileInfo(String memberId);

    ResponseMemberProfileDto getMemberProfileInfo(Long id);

    ResponseMemberProfileDto getMemberProfileInfoByNickName(String nickName);

    List<ResponseFindMemberProfileDto> getMemberProfileListInfoByNickName(Long memberId, String nickName);

    ResponseMemberInfoDto getMemberInfo(Long memberId);

    Long getMemberPk(String memberId);

    List<Long> getRandomMemberInfoId(List<Long> followers);

    Long getMemberIdByNickName(String nickName);

    void updateMemberSetting(Long id, RequestMemberSettingDto memberSettingDto);

    ResponseMemberProfileDto updateMemberInfo(Long id, RequestUpdateMemberInfoDto memberInfoUpdateDto, MultipartFile profileImg);

    List<ResponseRecipientDto> getRecipientsInfo(List<Long> recipientIds);

    MemberInfo getMemberInfoEntity(Long memberId);

    void deleteMember(Long memberId);

    void deleteMemberHistory(Long memberId);
}
