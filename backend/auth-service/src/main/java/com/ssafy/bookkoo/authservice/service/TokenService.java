package com.ssafy.bookkoo.authservice.service;

import com.ssafy.bookkoo.authservice.entity.Member;
import org.springframework.stereotype.Service;

@Service
public interface TokenService {

    String createRefreshToken(Member member);

    String createAccessToken(Member member);

    String updateRefreshToken(Member member);
}
