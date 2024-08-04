package com.ssafy.bookkoo.authservice.service.Impl;

import com.ssafy.bookkoo.authservice.dto.RequestLoginDto;
import com.ssafy.bookkoo.authservice.dto.ResponseLoginTokenDto;
import com.ssafy.bookkoo.authservice.entity.Member;
import com.ssafy.bookkoo.authservice.entity.SocialType;
import com.ssafy.bookkoo.authservice.exception.LoginFailException;
import com.ssafy.bookkoo.authservice.exception.MemberNotFoundException;
import com.ssafy.bookkoo.authservice.repository.MemberRepository;
import com.ssafy.bookkoo.authservice.service.AuthService;
import com.ssafy.bookkoo.authservice.service.TokenService;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public ResponseLoginTokenDto login(RequestLoginDto requestLoginDto) {
        Member member = memberRepository.findByEmail(requestLoginDto.email())
                                        .orElseThrow(() -> new MemberNotFoundException(
                                            requestLoginDto.email()));
        //matches(평문, 암호문)으로 확인
        boolean matches = passwordEncoder.matches(requestLoginDto.password(), member.getPassword());
        if (!matches) {
            throw new LoginFailException();
        }
        return getResponseLoginTokenDto(member);
    }

    /**
     * 소셜 로그인 시 사용되는 서비스 로직
     *
     * @param email
     * @return
     */
    @Override
    @Transactional
    public ResponseLoginTokenDto login(String email) {
        Member member = memberRepository.findByEmail(email)
                                        .orElseThrow(() -> new MemberNotFoundException(email));
        return getResponseLoginTokenDto(member);
    }


    /**
     * 토큰으로 멤버ID를 찾고 이를 통해
     * 새로운 토큰 발급
     * 리프레시 토큰 검증 X
     * Redis TTL과 동일한 시간으로 설정했기 때문에
     * Redis에 존재하지 않으면 만료된 것
     *
     * @param refreshToken
     * @return
     */
    @Override
    @Transactional
    public ResponseLoginTokenDto getTokenDto(String refreshToken) {
        String memberId = tokenService.getMemberIdByRefreshToken(refreshToken);
        Member member = memberRepository.findByMemberId(memberId)
                                        .orElseThrow(MemberNotFoundException::new);
        return getResponseLoginTokenDto(member);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Member> getMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }


    /**
     * 토큰을 갱신하고 액세스 토큰을 생성하는 메서드
     * @param member
     * @return
     */
    @Override
    @Transactional
    public ResponseLoginTokenDto getResponseLoginTokenDto(Member member) {
        String refreshToken = tokenService.updateRefreshToken(member);
        String accessToken = tokenService.createAccessToken(member.getMemberId());
        return ResponseLoginTokenDto.builder()
                                    .accessToken(accessToken)
                                    .refreshToken(refreshToken)
                                    .build();
    }


    /**
     * 개발용 서비스 메서드
     */
    @Override
    public ResponseLoginTokenDto getDeveloperTokenDto() {
        String email = "test@test";
        Optional<Member> optionalMember = getMemberByEmail(email);
        Member member = null;
        if (optionalMember.isEmpty()) {
            member = Member.builder()
                           .email(email)
                           .socialType(SocialType.bookkoo)
                           .password("test123$")
                           .memberId(UUID.randomUUID()
                                         .toString())
                           .build();
            memberRepository.save(member);
        } else {
            member = optionalMember.get();
        }
        return getResponseLoginTokenDto(member);
    }

    /**
     * 멤버 로그아웃
     * Redis의 리프레시 토큰 제거
     * @param id
     */
    @Override
    @Transactional
    public void logout(Long id) {
        Member member = memberRepository.findById(id)
                                        .orElseThrow(MemberNotFoundException::new);
        String memberId = member.getMemberId();
        tokenService.deleteRefreshToken(memberId);
    }
}
