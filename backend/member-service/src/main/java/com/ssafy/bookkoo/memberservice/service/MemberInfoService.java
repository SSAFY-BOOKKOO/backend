package com.ssafy.bookkoo.memberservice.service;

import com.ssafy.bookkoo.memberservice.dto.request.RequestUpdatePasswordDto;
import com.ssafy.bookkoo.memberservice.dto.response.ResponseMemberInfoDto;
import com.ssafy.bookkoo.memberservice.dto.response.ResponseMemberProfileDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MemberInfoService {

    void updatePassword(Long id, RequestUpdatePasswordDto requestUpdatePasswordDto);

    void updateProfileImg(Long id, MultipartFile profileImg);

    ResponseMemberProfileDto getMemberProfileInfo(String memberId);

    ResponseMemberProfileDto getMemberProfileInfo(Long id);

    ResponseMemberInfoDto getMemberInfo(Long memberId);

    Long getMemberPk(String memberId);

    List<Long> getRandomMemberInfo(List<Long> followers);

    Long getMemberIdByNickName(String nickName);
}
