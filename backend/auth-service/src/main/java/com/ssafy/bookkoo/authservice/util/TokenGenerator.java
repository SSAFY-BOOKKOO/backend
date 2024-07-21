package com.ssafy.bookkoo.authservice.util;

import com.ssafy.bookkoo.authservice.config.JwtConfig;
import com.ssafy.bookkoo.authservice.entity.Member;
import io.jsonwebtoken.Jwts;
import java.time.Duration;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenGenerator {

    private final JwtConfig jwtConfig;

    /**
     * 새로운 토큰 생성
     * @param member
     * @param expiredAt
     * @return
     */
    public String generateToken(Member member, Duration expiredAt) {
        Date now = new Date();
        return Jwts.builder()
                   .issuer(jwtConfig.getIssuer())
                   .issuedAt(now)
                   .expiration(new Date(now.getTime() + expiredAt.toMillis()))
                   .subject(member.getMemberId()
                                  .toString())
                   .signWith(jwtConfig.getSecretKey())
                   .compact();
    }
}
