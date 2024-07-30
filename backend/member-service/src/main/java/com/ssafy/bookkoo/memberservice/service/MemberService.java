package com.ssafy.bookkoo.memberservice.service;

import com.ssafy.bookkoo.memberservice.dto.request.RequestAdditionalInfo;
import com.ssafy.bookkoo.memberservice.dto.request.RequestCertificationDto;
import com.ssafy.bookkoo.memberservice.dto.request.RequestRegisterDto;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {

    String register(RequestRegisterDto requestRegisterDto);

    String register(String email);

    boolean checkDuplEmail(String email);

    void sendCertiNumToEmail(String email);

    boolean checkValidationEmail(RequestCertificationDto requestCertificationDto);

    void passwordReset(String email);

    boolean checkDuplNickName(String nickName);

    String registerAdditionalInfo(RequestAdditionalInfo requestAdditionalInfo,
        MultipartFile profileImg);
}
