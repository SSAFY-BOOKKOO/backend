package com.ssafy.bookkoo.authservice.service.Impl;

import com.ssafy.bookkoo.authservice.entity.Member;
import com.ssafy.bookkoo.authservice.entity.RefreshToken;
import com.ssafy.bookkoo.authservice.repository.RefreshTokenRepository;
import com.ssafy.bookkoo.authservice.service.TokenService;
import com.ssafy.bookkoo.authservice.util.TokenProvider;
import java.time.Duration;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenProvider tokenProvider;

    private final RefreshTokenRepository refreshTokenRepository;
    private final Duration ACCESS_TOKEN_EXPIRATION = Duration.ofHours(1);
    private final Duration REFRESH_TOKEN_EXPIRATION = Duration.ofDays(7);


    /**
     * 새로운 리프레시 토큰 발급 및 DB저장
     *
     * @param member
     * @return
     */
    @Override
    @Transactional
    public String createRefreshToken(Member member) {
        RefreshToken refreshToken = RefreshToken.builder()
                                                .memberId(member.getMemberId()
                                                                .toString())
                                                .refreshToken(tokenProvider.generateToken(member,
                                                    REFRESH_TOKEN_EXPIRATION))
                                                .ttl(REFRESH_TOKEN_EXPIRATION.getSeconds())
                                                .build();

        return refreshTokenRepository.save(refreshToken)
                                     .getRefreshToken();
    }

    /**
     * 1시간 유효기간의 액세스 토큰 발급
     *
     * @param member
     * @return
     */
    @Override
    public String createAccessToken(Member member) {
        return tokenProvider.generateToken(member, ACCESS_TOKEN_EXPIRATION);
    }

    /**
     * RTR 방식을 위해 리프레시 토큰으로 액세스 토큰 발급 시
     * 리프레시 토큰 갱신하여 저장 및 다시 반환
     *
     * @param member
     * @return
     */
    @Override
    @Transactional
    public String updateRefreshToken(Member member) {
        Optional<RefreshToken> optionalRefreshToken
            = refreshTokenRepository.findByMemberId(member.getMemberId()
                                                          .toString());

        //리프레시 토근이 존재하면 없애기
        optionalRefreshToken.ifPresent(refreshTokenRepository::delete);

        //새로운 토큰 생성
        return createRefreshToken(member);
    }
}
