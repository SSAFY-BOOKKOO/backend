package com.ssafy.bookkoo.authservice.util;

import com.ssafy.bookkoo.authservice.config.JwtConfig;
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
     * @param uuid (리프레시 토큰 : 랜덤, 액세스 토큰 : 멤버 UUID)
     * @param expiredAt
     * @return
     */
    public String generateToken(String uuid, Duration expiredAt) {
        Date now = new Date();
        return Jwts.builder()
                   .issuer(jwtConfig.getIssuer())
                   .issuedAt(now)
                   .expiration(new Date(now.getTime() + expiredAt.toMillis()))
                   .subject(uuid)
                   .signWith(jwtConfig.getSecretKey())
                   .compact();
    }
}
