package com.ssafy.bookkoo.memberservice.service;

import com.ssafy.bookkoo.memberservice.dto.request.RequestUpdatePasswordDto;
import com.ssafy.bookkoo.memberservice.dto.response.ResponseMemberInfoDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MemberInfoService {

    void updatePassword(RequestUpdatePasswordDto requestUpdatePasswordDto);

    void updateProfileImg(String memberId, MultipartFile profileImg);

    ResponseMemberInfoDto getMemberInfo(String memberId);

    ResponseMemberInfoDto getMemberInfo(Long memberId);

    Long getMemberPk(String memberId);

    List<Long> getRandomMemberInfo(List<Long> followers);

    Long getMemberIdByNickName(String nickName);
}
