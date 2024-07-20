package com.ssafy.bookkoo.authservice.util;

import com.ssafy.bookkoo.authservice.config.JwtConfig;
import com.ssafy.bookkoo.authservice.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenProvider {

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

    /**
     * 토큰 유효성 검사
     * @param token
     * @return
     */
    public boolean validToken(String token) {
        try {
            Jwts.parser()
                .verifyWith(jwtConfig.getSecretKey())
                .build()
                .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 토큰에서 인증 객체 뽑아내기
     * @param token
     * @return
     */
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
        return new UsernamePasswordAuthenticationToken(
                new User(claims.getSubject(), "", authorities),
                token, authorities);
    }

    /**
     * 토큰에서 Claims뽑아내기
     * @param token
     * @return
     */
    private Claims getClaims(String token) {
        return Jwts.parser()
                   .verifyWith(jwtConfig.getSecretKey())
                   .build()
                   .parseSignedClaims(token)
                   .getPayload();
    }

}
