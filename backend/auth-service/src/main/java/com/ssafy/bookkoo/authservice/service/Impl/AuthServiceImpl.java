package com.ssafy.bookkoo.authservice.service.Impl;

import com.ssafy.bookkoo.authservice.dto.RequestLoginDto;
import com.ssafy.bookkoo.authservice.dto.ResponseLoginTokenDto;
import com.ssafy.bookkoo.authservice.entity.Member;
import com.ssafy.bookkoo.authservice.exception.LoginFailException;
import com.ssafy.bookkoo.authservice.exception.MemberNotFoundException;
import com.ssafy.bookkoo.authservice.repository.MemberRepository;
import com.ssafy.bookkoo.authservice.service.AuthService;
import com.ssafy.bookkoo.authservice.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;


    /**
     * 요청으로 받은 이메일을 통해 멤버를 찾는다.
     * 찾은 멤버의 DB에 저장된 암호화된 비밀번호와
     * 요청으로 받은 평문 비밀번호를 passwordEncoder를 통해 비교한다.
     * 같다면 리프레시 토큰과 엑세스 토큰을 발급한다.
     *
     * @param requestLoginDto
     */
    @Override
    public ResponseLoginTokenDto login(RequestLoginDto requestLoginDto) {
        Member member = memberRepository.findByEmail(requestLoginDto.email())
                                        .orElseThrow(() -> new MemberNotFoundException(
                                            requestLoginDto.email()));
        //matches(평문, 암호문)으로 확인
        boolean matches = passwordEncoder.matches(requestLoginDto.password(), member.getPassword());
        if (!matches) {
            throw new LoginFailException();
        }
        String refreshToken = tokenService.updateRefreshToken(member);
        String accessToken = tokenService.createAccessToken(member);
        return ResponseLoginTokenDto.builder()
                                    .accessToken(accessToken)
                                    .refreshToken(refreshToken)
                                    .build();
    }
}
