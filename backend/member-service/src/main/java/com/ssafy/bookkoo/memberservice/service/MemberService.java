package com.ssafy.bookkoo.memberservice.service;

import com.ssafy.bookkoo.memberservice.dto.RequestCertificationDto;
import com.ssafy.bookkoo.memberservice.dto.RequestRegisterDto;

public interface MemberService {

    void register(RequestRegisterDto requestRegisterDto);

    boolean checkDuplEmail(String email);

    void sendCertiNumToEmail(String email);

    boolean checkValidationEmail(RequestCertificationDto requestCertificationDto);

    void resetPassword(String email);
}
