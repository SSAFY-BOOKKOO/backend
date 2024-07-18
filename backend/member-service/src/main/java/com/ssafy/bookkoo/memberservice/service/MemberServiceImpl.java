package com.ssafy.bookkoo.memberservice.service;

import com.ssafy.bookkoo.memberservice.dto.RequestCertificationDto;
import com.ssafy.bookkoo.memberservice.dto.RequestRegisterDto;
import com.ssafy.bookkoo.memberservice.entity.CertificationNumber;
import com.ssafy.bookkoo.memberservice.entity.Member;
import com.ssafy.bookkoo.memberservice.exception.EmailNotValidException;
import com.ssafy.bookkoo.memberservice.repository.CertificationRepository;
import com.ssafy.bookkoo.memberservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final CertificationRepository certificationRepository;
    private final PasswordEncoder passwordEncoder;
    private final Long EXPIRED_TIME = 1800L; //30분 만료 시간

    /**
     * RequestRegisterDto를 통해 새로운 멤버를 생성
     * 사전조건
     * 1. 이메일 중복 체크
     * 2. 이메일 인증
     *
     * @param requestRegisterDto
     */
    @Override
    public void register(RequestRegisterDto requestRegisterDto) {
        Member member = Member.builder()
                              .memberId(UUID.randomUUID())
                              .email(requestRegisterDto.email())
                              .password(passwordEncoder.encode(requestRegisterDto.password()))
                              .isSocial(Boolean.FALSE)
                              .build();

        memberRepository.save(member);
    }

    /**
     * 이메일을 입력받아 중복체크
     *
     * @param email
     * @return 중복이 아니면 ? true : false
     */
    @Override
    public boolean checkDuplEmail(String email) {
        return memberRepository.findByEmail(email)
                               .isEmpty();
    }

    /**
     * 이메일로 인증번호를 전송
     * 인증번호를 이메일, 인증번호로 Redis에 저장
     *
     * @param email
     */
    @Override
    public void sendCertiNumToEmail(String email) {
        CertificationNumber certificationNumber = CertificationNumber.builder()
                                                                     .email(email)
                                                                     .certNum(UUID.randomUUID()
                                                                                  .toString())
                                                                     .ttl(EXPIRED_TIME)
                                                                     .build();
        certificationRepository.save(certificationNumber);
    }

    /**
     * 인증번호 발송을 통해 얻은 인증번호를 검증
     * 레디스에 30분 유효기간을 가지고 검증 수행
     * 검증 성공 시 해당 데이터 Redis에서 삭제
     * 검증 실패 시 EmailNotValidException 예외 발생
     * @param requestCertificationDto
     * @return
     */
    @Override
    public boolean checkValidationEmail(RequestCertificationDto requestCertificationDto) {
        CertificationNumber certificationNumber = certificationRepository.findByEmailAndCertNum(requestCertificationDto.email(), requestCertificationDto.certNum())
                                                                         .orElseThrow(EmailNotValidException::new);
        certificationRepository.delete(certificationNumber);
        return true;
    }


    /**
     * 비밀번호 초기화 및 이메일로 초기화된 비밀번호 전송
     *
     * @param email
     */
    @Override
    public void resetPassword(String email) {

    }
}
