package com.ssafy.bookkoo.memberservice.service;

import com.ssafy.bookkoo.memberservice.dto.request.RequestCertificationDto;
import com.ssafy.bookkoo.memberservice.client.dto.request.RequestLoginDto;
import com.ssafy.bookkoo.memberservice.dto.request.RequestRegisterMemberDto;
import com.ssafy.bookkoo.memberservice.client.dto.response.ResponseLoginTokenDto;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {

    RequestLoginDto register(RequestRegisterMemberDto requestRegisterMemberDto, MultipartFile profileImg);

    boolean checkDuplEmail(String email);

    void sendCertiNumToEmail(String email);

    boolean checkValidationEmail(RequestCertificationDto certificationDto);

    void passwordReset(String email);

    boolean checkDuplNickName(String nickName);

    ResponseLoginTokenDto registerLogin(RequestLoginDto loginDto);
}
