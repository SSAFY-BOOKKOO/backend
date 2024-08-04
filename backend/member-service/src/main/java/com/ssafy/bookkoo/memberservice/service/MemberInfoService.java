package com.ssafy.bookkoo.memberservice.service;

import com.ssafy.bookkoo.memberservice.dto.request.RequestMemberSettingDto;
import com.ssafy.bookkoo.memberservice.dto.request.RequestUpdateMemberInfoDto;
import com.ssafy.bookkoo.memberservice.dto.request.RequestUpdatePasswordDto;
import com.ssafy.bookkoo.memberservice.dto.response.ResponseMemberInfoDto;
import com.ssafy.bookkoo.memberservice.dto.response.ResponseMemberProfileDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MemberInfoService {

    void updatePassword(Long id, RequestUpdatePasswordDto requestUpdatePasswordDto);

    ResponseMemberProfileDto getMemberProfileInfo(String memberId);

    ResponseMemberProfileDto getMemberProfileInfo(Long id);

    ResponseMemberInfoDto getMemberInfo(Long memberId);

    Long getMemberPk(String memberId);

    List<Long> getRandomMemberInfo(List<Long> followers);

    Long getMemberIdByNickName(String nickName);

    void updateMemberSetting(Long id, RequestMemberSettingDto memberSettingDto);

    void updateMemberInfo(Long id, RequestUpdateMemberInfoDto memberInfoUpdateDto, MultipartFile profileImg);
}
