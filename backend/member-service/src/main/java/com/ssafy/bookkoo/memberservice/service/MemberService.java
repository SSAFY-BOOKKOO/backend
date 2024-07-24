package com.ssafy.bookkoo.memberservice.service;

import com.ssafy.bookkoo.memberservice.dto.RequestAdditionalInfo;
import com.ssafy.bookkoo.memberservice.dto.RequestCertificationDto;
import com.ssafy.bookkoo.memberservice.dto.RequestRegisterDto;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {

    String register(RequestRegisterDto requestRegisterDto);

    boolean checkDuplEmail(String email);

    void sendCertiNumToEmail(String email);

    boolean checkValidationEmail(RequestCertificationDto requestCertificationDto);

    void passwordReset(String email);

    boolean checkDuplNickName(String nickName);

    String registerAdditionalInfo(RequestAdditionalInfo requestAdditionalInfo,
        MultipartFile profileImg);
}
