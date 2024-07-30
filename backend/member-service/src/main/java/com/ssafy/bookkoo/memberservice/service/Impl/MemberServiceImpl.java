package com.ssafy.bookkoo.memberservice.service.Impl;

import com.ssafy.bookkoo.memberservice.client.AuthServiceClient;
import com.ssafy.bookkoo.memberservice.client.CommonServiceClient;
import com.ssafy.bookkoo.memberservice.dto.request.RequestAdditionalInfo;
import com.ssafy.bookkoo.memberservice.dto.request.RequestCertificationDto;
import com.ssafy.bookkoo.memberservice.dto.request.RequestLoginDto;
import com.ssafy.bookkoo.memberservice.dto.request.RequestRegisterDto;
import com.ssafy.bookkoo.memberservice.dto.request.RequestRegisterDto.RequestRegisterDtoBuilder;
import com.ssafy.bookkoo.memberservice.dto.request.RequestRegisterMemberDto;
import com.ssafy.bookkoo.memberservice.dto.response.ResponseLoginTokenDto;
import com.ssafy.bookkoo.memberservice.entity.CertificationNumber;
import com.ssafy.bookkoo.memberservice.entity.Member;
import com.ssafy.bookkoo.memberservice.entity.Member.MemberBuilder;
import com.ssafy.bookkoo.memberservice.entity.MemberCategoryMapper;
import com.ssafy.bookkoo.memberservice.entity.MemberCategoryMapperKey;
import com.ssafy.bookkoo.memberservice.entity.MemberInfo;
import com.ssafy.bookkoo.memberservice.entity.SocialType;
import com.ssafy.bookkoo.memberservice.exception.EmailDuplicateException;
import com.ssafy.bookkoo.memberservice.exception.EmailNotValidException;
import com.ssafy.bookkoo.memberservice.exception.EmailSendFailException;
import com.ssafy.bookkoo.memberservice.exception.MemberNotFoundException;
import com.ssafy.bookkoo.memberservice.exception.NickNameDuplicateException;
import com.ssafy.bookkoo.memberservice.repository.CertificationRepository;
import com.ssafy.bookkoo.memberservice.repository.MemberCategoryMapperRepository;
import com.ssafy.bookkoo.memberservice.repository.MemberInfoRepository;
import com.ssafy.bookkoo.memberservice.repository.MemberRepository;
import com.ssafy.bookkoo.memberservice.service.MailSendService;
import com.ssafy.bookkoo.memberservice.service.MemberService;
import java.util.Arrays;
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

    private final Long EXPIRED_TIME = 1800L; //30분 만료 시간

    private final MemberRepository memberRepository;
    private final MemberInfoRepository memberInfoRepository;
    private final CertificationRepository certificationRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailSendService mailSendService;
    private final MemberCategoryMapperRepository memberCategoryMapperRepository;


    //S3 common 서비스
    private final CommonServiceClient commonServiceClient;
    private final AuthServiceClient authServiceClient;

    /**
     * 회원가입에 필요한 모든 정보를 받아 회원가입하는 서비스
     *
     * @param requestRegisterMemberDto
     */
    @Override
    @Transactional
    public ResponseLoginTokenDto register(RequestRegisterMemberDto requestRegisterMemberDto, MultipartFile profileImg) {
        //회원 정보 저장을 위한 DTO
        RequestRegisterDtoBuilder registerDtoBuilder
            = RequestRegisterDto.builder()
                                .email(requestRegisterMemberDto.email())
                                .socialType(requestRegisterMemberDto.socialType());

        //이메일 중복 확인
        checkDuplEmail(requestRegisterMemberDto.email());
        //닉네임 중복 확인
        checkDuplNickName(requestRegisterMemberDto.nickName());
        //비밀번호 유효성 검증 (bookkoo 도메인인 경우 : 이메일 가입)
        if (requestRegisterMemberDto.socialType() == SocialType.bookkoo) {
            registerDtoBuilder.password(requestRegisterMemberDto.password());
        }

        //회원정보 등록
        Member member = registerMember(registerDtoBuilder.build());

        //추가정보 저장을 위한 DTO
        RequestAdditionalInfo additionalInfo
            = RequestAdditionalInfo.builder()
                                   .id(member.getId())
                                   .memberId(member.getMemberId())
                                   .categories(requestRegisterMemberDto.categories())
                                   .gender(requestRegisterMemberDto.gender())
                                   .introduction(requestRegisterMemberDto.introduction())
                                   .nickName(requestRegisterMemberDto.nickName())
                                   .build();
        //추가정보 등록
        registerAdditionalInfo(additionalInfo, profileImg);
        //TODO 토큰 받아서 반환 근데 auth로 요청하면 쿠키에 토큰이 들어갈까?
        RequestLoginDto loginDto = RequestLoginDto.builder()
                                                  .email(requestRegisterMemberDto.email())
                                                  .password(requestRegisterMemberDto.password())
                                                  .build();
        return authServiceClient.login(loginDto);
    }

    /**
     * RequestRegisterDto를 통해 새로운 멤버를 생성
     * 사전조건
     * 1. 이메일 중복 체크
     * 2. 이메일 인증
     *
     * @param requestRegisterDto
     */
    @Override
    @Transactional
    public Member registerMember(RequestRegisterDto requestRegisterDto) {
        MemberBuilder memberBuilder = Member.builder()
                                     .memberId(UUID.randomUUID()
                                                   .toString())
                                     .email(requestRegisterDto.email())
                                     .socialType(requestRegisterDto.socialType());
        String password = requestRegisterDto.password();
        //password가 null이 아니면 추가 (소셜 로그인인 경우 추가하지 않기 위해)
        if (password != null) {
            memberBuilder.password(passwordEncoder.encode(password));
        }
        Member member = memberBuilder.build();
        return memberRepository.save(member);
    }

    /**
     * 이메일을 통해 추가정보 등록
     *
     * @param requestAdditionalInfo
     * @param profileImg
     */
    @Override
    @Transactional
    public void registerAdditionalInfo(RequestAdditionalInfo requestAdditionalInfo,
        MultipartFile profileImg) {
        log.info(requestAdditionalInfo.memberId());
        Member member = memberRepository.findByMemberId(requestAdditionalInfo.memberId())
                                        .orElseThrow(MemberNotFoundException::new);


        //기본 프로필 이미지
        String fileKey = "Default.jpg";
        if (profileImg != null) {
            fileKey = commonServiceClient.saveProfileImg(profileImg, null);
        }
        MemberInfo memberInfo = MemberInfo.builder()
                                          .id(member.getId())
                                          .memberId(member.getMemberId())
                                          .gender(requestAdditionalInfo.gender())
                                          .nickName(requestAdditionalInfo.nickName())
                                          .year(requestAdditionalInfo.year())
                                          .introduction(requestAdditionalInfo.introduction())
                                          .profileImgUrl(fileKey)
                                          .build();

        //추가 정보 저장
        MemberInfo info = memberInfoRepository.save(memberInfo);
        //선호 카테고리 추가
        saveCategories(requestAdditionalInfo, info);
    }

    /**
     * 선호 카테고리를 저장하는 메서드
     * @param requestAdditionalInfo
     * @param info
     */
    private void saveCategories(RequestAdditionalInfo requestAdditionalInfo, MemberInfo info) {
        Arrays.stream(requestAdditionalInfo.categories())
              .forEach((categoryId) -> {
                  //멤버 매퍼 키를 생성
                  MemberCategoryMapperKey memberCategoryMapperKey
                      = MemberCategoryMapperKey.builder()
                                               .categoryId(categoryId)
                                               .memberInfoId(info.getId())
                                               .build();
                  //매퍼키를 통해 매퍼 엔티티 생성
                  MemberCategoryMapper memberCategoryMapper
                      = MemberCategoryMapper.builder()
                                            .memberCategoryMapperKey(
                                                memberCategoryMapperKey)
                                            .memberInfo(info)
                                            .build();
                  //매퍼 테이블에 매퍼 정보 저장
                  memberCategoryMapperRepository.save(memberCategoryMapper);
              });
    }

    /**
     * 이메일을 입력받아 중복체크
     *
     * @param email
     * @return 중복이 아니면 ? true : false
     */
    @Override
    @Transactional(readOnly = true)
    public boolean checkDuplEmail(String email) {
        boolean empty = memberRepository.findByEmail(email)
                                        .isEmpty();
        if (!empty) {
            throw new EmailDuplicateException();
        }
        return true;
    }

    /**
     * 닉네임 중복 체크
     *
     * @param nickName
     */
    @Override
    @Transactional(readOnly = true)
    public boolean checkDuplNickName(String nickName) {
        boolean empty = memberInfoRepository.findByNickName(nickName)
                                            .isEmpty();
        if (!empty) {
            throw new NickNameDuplicateException();
        }
        return true;
    }


    /**
     * 이메일로 인증번호를 전송
     * 인증번호를 이메일, 인증번호로 Redis에 저장
     *
     * @param email
     */
    @Override
    @Transactional
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
            log.info("mail send Exception : {}", e.getMessage());
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
    @Transactional
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
    @Transactional
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
}
