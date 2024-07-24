package com.ssafy.bookkoo.memberservice.service;

import com.ssafy.bookkoo.memberservice.dto.RequestUpdatePasswordDto;
import com.ssafy.bookkoo.memberservice.dto.ResponseMemberInfoDto;
import org.springframework.web.multipart.MultipartFile;

public interface MemberInfoService {

    void updatePassword(RequestUpdatePasswordDto requestUpdatePasswordDto);

    void updateProfileImg(String memberId, MultipartFile profileImg);

    ResponseMemberInfoDto getMemberInfo(String memberId);

    Long getMemberPk(String memberId);
}
