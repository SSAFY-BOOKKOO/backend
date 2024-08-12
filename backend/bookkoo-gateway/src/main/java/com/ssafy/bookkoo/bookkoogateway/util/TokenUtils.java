package com.ssafy.bookkoo.bookkoogateway.util;

import com.ssafy.bookkoo.bookkoogateway.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.Collections;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenUtils {

    private final JwtConfig jwtConfig;

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

    /**
     * 토큰에서 MemberId를 뽑아내기 위한 메서드
     * @param token
     * @return
     */
    public String getSubject(String token) {
        return Jwts.parser()
                   .verifyWith(jwtConfig.getSecretKey())
                   .build()
                   .parseSignedClaims(token)
                   .getPayload()
                   .getSubject();
    }

}
