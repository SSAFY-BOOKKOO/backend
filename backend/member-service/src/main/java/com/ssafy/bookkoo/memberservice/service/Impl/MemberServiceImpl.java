package com.ssafy.bookkoo.memberservice.service.Impl;

import com.ssafy.bookkoo.memberservice.client.CommonServiceClient;
import com.ssafy.bookkoo.memberservice.dto.request.RequestAdditionalInfo;
import com.ssafy.bookkoo.memberservice.dto.request.RequestCertificationDto;
import com.ssafy.bookkoo.memberservice.dto.request.RequestRegisterDto;
import com.ssafy.bookkoo.memberservice.entity.CertificationNumber;
import com.ssafy.bookkoo.memberservice.entity.Member;
import com.ssafy.bookkoo.memberservice.entity.MemberInfo;
import com.ssafy.bookkoo.memberservice.exception.EmailNotValidException;
import com.ssafy.bookkoo.memberservice.exception.EmailSendFailException;
import com.ssafy.bookkoo.memberservice.exception.MemberNotFoundException;
import com.ssafy.bookkoo.memberservice.repository.CertificationRepository;
import com.ssafy.bookkoo.memberservice.repository.MemberInfoRepository;
import com.ssafy.bookkoo.memberservice.repository.MemberRepository;
import com.ssafy.bookkoo.memberservice.service.MailSendService;
import com.ssafy.bookkoo.memberservice.service.MemberService;
import java.util.Collections;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberInfoRepository memberInfoRepository;
    private final CertificationRepository certificationRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailSendService mailSendService;
    private final Long EXPIRED_TIME = 1800L; //30분 만료 시간

    //S3 common 서비스
    private final CommonServiceClient commonServiceClient;

    /**
     * RequestRegisterDto를 통해 새로운 멤버를 생성
     * 사전조건
     * 1. 이메일 중복 체크
     * 2. 이메일 인증
     *
     * @param requestRegisterDto
     */
    @Override
    public String register(RequestRegisterDto requestRegisterDto) {
        Member member = Member.builder()
                              .memberId(UUID.randomUUID().toString())
                              .email(requestRegisterDto.email())
                              .password(passwordEncoder.encode(requestRegisterDto.password()))
                              .isSocial(Boolean.FALSE)
                              .build();

        Member save = memberRepository.save(member);
        return save.getMemberId();
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
        String certNum = UUID.randomUUID()
                            .toString();
        CertificationNumber certificationNumber = CertificationNumber.builder()
                                                                     .email(email)
                                                                     .certNum(certNum)
                                                                     .ttl(EXPIRED_TIME)
                                                                     .build();

        certificationRepository.save(certificationNumber);
        try {
            mailSendService.sendMail("북꾸북꾸 이메일 인증번호", certNum, Collections.singletonList(email));
        } catch (Exception e) {
            throw new EmailSendFailException();
        }
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
    public void passwordReset(String email) {
        String password = UUID.randomUUID()
                              .toString();
        Member member = memberRepository.findByEmail(email)
                                        .orElseThrow(() -> new MemberNotFoundException(email));
        try {
            mailSendService.sendMail("북꾸북꾸 임시 비밀번호 발급", password, Collections.singletonList(email));
            member.setPassword(passwordEncoder.encode(password));
            memberRepository.save(member);
            memberRepository.flush();
        } catch (Exception e) {
            throw new EmailSendFailException();
        }
    }

    /**
     * 닉네임 중복 체크
     *
     * @param nickName
     */
    @Override
    public boolean checkDuplNickName(String nickName) {
        return memberInfoRepository.findByNickName(nickName)
                                   .isEmpty();
    }

    /**
     * 이메일을 통해 추가정보 등록
     *
     * @param requestAdditionalInfo
     * @param profileImg
     */
    @Override
    @Transactional
    public String registerAdditionalInfo(RequestAdditionalInfo requestAdditionalInfo,
        MultipartFile profileImg) {
        log.info(requestAdditionalInfo.memberId());
        Member member = memberRepository.findByMemberId(requestAdditionalInfo.memberId())
                                        .orElseThrow(MemberNotFoundException::new);

        String fileKey = commonServiceClient.saveProfileImg(profileImg, null);
        MemberInfo memberInfo = MemberInfo.builder()
                                          .memberId(member.getMemberId())
                                          .nickName(requestAdditionalInfo.nickName())
                                          .year(requestAdditionalInfo.year())
                                          .introduction(requestAdditionalInfo.introduction())
                                          .profileImgUrl(fileKey)
                                          .build();

        memberInfoRepository.save(memberInfo);
        return fileKey;
    }

}
