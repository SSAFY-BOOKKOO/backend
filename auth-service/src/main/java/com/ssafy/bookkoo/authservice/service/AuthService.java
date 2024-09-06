package com.ssafy.bookkoo.authservice.service;

import com.ssafy.bookkoo.authservice.dto.RequestLoginDto;
import com.ssafy.bookkoo.authservice.dto.ResponseLoginTokenDto;
import com.ssafy.bookkoo.authservice.entity.Member;
import java.util.Optional;

public interface AuthService {

    ResponseLoginTokenDto login(RequestLoginDto requestLoginDto);

    ResponseLoginTokenDto login(String email);

    ResponseLoginTokenDto getTokenDto(String refreshToken);

    Optional<Member> getMemberByEmail(String email);

    ResponseLoginTokenDto getResponseLoginTokenDto(Member member);

    ResponseLoginTokenDto getDeveloperTokenDto();

    void logout(Long memberId);
}
