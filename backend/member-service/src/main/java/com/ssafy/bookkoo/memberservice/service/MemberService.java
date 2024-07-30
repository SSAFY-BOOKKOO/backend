package com.ssafy.bookkoo.memberservice.service;

import com.ssafy.bookkoo.memberservice.dto.request.RequestAdditionalInfo;
import com.ssafy.bookkoo.memberservice.dto.request.RequestCertificationDto;
import com.ssafy.bookkoo.memberservice.dto.request.RequestRegisterDto;
import com.ssafy.bookkoo.memberservice.dto.request.RequestRegisterMemberDto;
import com.ssafy.bookkoo.memberservice.dto.response.ResponseLoginTokenDto;
import com.ssafy.bookkoo.memberservice.entity.Member;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {

    ResponseLoginTokenDto register(RequestRegisterMemberDto requestRegisterMemberDto, MultipartFile profileImg);

    Member registerMember(RequestRegisterDto requestRegisterDto);

    boolean checkDuplEmail(String email);

    void sendCertiNumToEmail(String email);

    boolean checkValidationEmail(RequestCertificationDto requestCertificationDto);

    void passwordReset(String email);

    boolean checkDuplNickName(String nickName);

    void registerAdditionalInfo(RequestAdditionalInfo requestAdditionalInfo,
        MultipartFile profileImg);
}
