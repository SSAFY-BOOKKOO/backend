package com.ssafy.bookkoo.authservice.service;

import com.ssafy.bookkoo.authservice.entity.Member;
import org.springframework.stereotype.Service;

@Service
public interface TokenService {

    String createRefreshToken(String memberId);

    String createAccessToken(String memberId);

    String updateRefreshToken(Member member);

    String getMemberIdByRefreshToken(String refreshToken);

    void deleteRefreshToken(String memberId);
}
