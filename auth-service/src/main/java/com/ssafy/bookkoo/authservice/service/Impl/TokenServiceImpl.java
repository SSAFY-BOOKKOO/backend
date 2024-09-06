package com.ssafy.bookkoo.authservice.service.Impl;

import com.ssafy.bookkoo.authservice.entity.Member;
import com.ssafy.bookkoo.authservice.entity.RefreshToken;
import com.ssafy.bookkoo.authservice.exception.TokenExpiredException;
import com.ssafy.bookkoo.authservice.repository.RefreshTokenRepository;
import com.ssafy.bookkoo.authservice.service.TokenService;
import com.ssafy.bookkoo.authservice.util.TokenGenerator;
import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenGenerator tokenGenerator;

    private final RefreshTokenRepository refreshTokenRepository;
    private final Duration ACCESS_TOKEN_EXPIRATION = Duration.ofDays(1);
    private final Duration REFRESH_TOKEN_EXPIRATION = Duration.ofDays(30);


    /**
     * 새로운 리프레시 토큰 발급 및 DB저장 리프레시 토큰은 의미없는 UUID로 설정
     *
     * @param memberId
     * @return
     */
    @Override
    @Transactional
    public String createRefreshToken(String memberId) {
        //공개될 수 있기 때문에 리프레시 토큰은 랜덤 UUID를 통해 생성하도록 변경
        String token = UUID.randomUUID()
                           .toString();
        RefreshToken refreshToken = RefreshToken.builder()
                                                .memberId(memberId)
                                                .refreshToken(tokenGenerator.generateToken(token,
                                                    REFRESH_TOKEN_EXPIRATION))
                                                .ttl(REFRESH_TOKEN_EXPIRATION.getSeconds())
                                                .build();
        return refreshTokenRepository.save(refreshToken)
                                     .getRefreshToken();
    }

    /**
     * 1시간 유효기간의 액세스 토큰 발급
     *
     * @param memberId
     * @return
     */
    @Override
    public String createAccessToken(String memberId) {
        return tokenGenerator.generateToken(memberId, ACCESS_TOKEN_EXPIRATION);
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
        deleteRefreshToken(member.getMemberId());
        //새로운 토큰 생성
        return createRefreshToken(member.getMemberId());
    }

    /**
     * 토큰을 통해 Redis에서 토큰에 대한 MemberId 반환
     * 이때, 토큰 만료도 함께 확인
     *
     * @param refreshToken
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public String getMemberIdByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                                     .orElseThrow(TokenExpiredException::new)
                                     .getMemberId();
    }

    @Override
    @Transactional
    public void deleteRefreshToken(String memberId) {
        Optional<RefreshToken> optionalRefreshToken
            = refreshTokenRepository.findByMemberId(memberId);
        //리프레시 토큰이 존재하면 없애기
        optionalRefreshToken.ifPresent(refreshTokenRepository::delete);
    }
}
