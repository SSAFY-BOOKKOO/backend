package com.ssafy.bookkoo.memberservice.service.Impl;

import com.ssafy.bookkoo.memberservice.client.AuthServiceClient;
import com.ssafy.bookkoo.memberservice.client.CommonServiceClient;
import com.ssafy.bookkoo.memberservice.client.LibraryServiceClient;
import com.ssafy.bookkoo.memberservice.client.dto.request.LibraryStyleDto;
import com.ssafy.bookkoo.memberservice.client.dto.request.RequestCreateLibraryDto;
import com.ssafy.bookkoo.memberservice.client.dto.request.RequestLoginDto;
import com.ssafy.bookkoo.memberservice.dto.request.*;
import com.ssafy.bookkoo.memberservice.dto.request.RequestRegisterDto.RequestRegisterDtoBuilder;
import com.ssafy.bookkoo.memberservice.client.dto.response.ResponseLoginTokenDto;
import com.ssafy.bookkoo.memberservice.entity.*;
import com.ssafy.bookkoo.memberservice.entity.Member.MemberBuilder;
import com.ssafy.bookkoo.memberservice.enums.SocialType;
import com.ssafy.bookkoo.memberservice.exception.*;
import com.ssafy.bookkoo.memberservice.repository.CertificationRepository;
import com.ssafy.bookkoo.memberservice.repository.MemberCategoryMapperRepository;
import com.ssafy.bookkoo.memberservice.repository.MemberInfoRepository;
import com.ssafy.bookkoo.memberservice.repository.MemberRepository;
import com.ssafy.bookkoo.memberservice.repository.MemberSettingRepository;
import com.ssafy.bookkoo.memberservice.service.MailSendService;
import com.ssafy.bookkoo.memberservice.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final Long EXPIRED_TIME = 1800L; //30분 만료 시간

    private final MemberRepository memberRepository;
    private final MemberInfoRepository memberInfoRepository;
    private final MemberSettingRepository memberSettingRepository;
    private final CertificationRepository certificationRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailSendService mailSendService;
    private final MemberCategoryMapperRepository memberCategoryMapperRepository;
    private final AuthServiceClient authServiceClient;
    private final LibraryServiceClient libraryServiceClient;

    //S3 common 서비스
    private final CommonServiceClient commonServiceClient;
    private final String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$";

    @Value("${config.default-img-url}")
    private String DEFAULT_IMG_URL;

    @Value("${config.member-bucket-name}")
    private String BUCKET;
    /**
     * 회원가입에 필요한 모든 정보를 받아 회원가입하는 서비스
     *
     * @param registerMemberDto
     */
    @Override
    @Transactional
    public void register(RequestRegisterMemberDto registerMemberDto, MultipartFile profileImg) {
        //회원 정보 저장을 위한 DTO
        RequestRegisterDtoBuilder registerDtoBuilder
            = RequestRegisterDto.builder()
                                .email(registerMemberDto.email())
                                .socialType(registerMemberDto.socialType());

        //이메일 중복 확인
        checkDuplEmail(registerMemberDto.email());
        //닉네임 중복 확인
        checkDuplNickName(registerMemberDto.nickName());
        //비밀번호 유효성 검증 (bookkoo 도메인인 경우 : 이메일 가입)
        if (registerMemberDto.socialType() == SocialType.bookkoo) {
            if (registerMemberDto.password()
                                        .matches(passwordRegex)) {
                registerDtoBuilder.password(registerMemberDto.password());

            } else {
                throw new PasswordNotValidException();
            }
        } else { //소셜 정보를 통해 가입한 경우 랜덤 비밀번호를 추가
            registerDtoBuilder.password(UUID.randomUUID()
                                            .toString());
        }

        //회원정보 등록
        Member member = registerMember(registerDtoBuilder.build());
        //추가정보 저장을 위한 DTO
        RequestAdditionalInfo additionalInfo
            = RequestAdditionalInfo.builder()
                                   .id(member.getId())
                                   .memberId(member.getMemberId())
                                   .categories(registerMemberDto.categories())
                                   .gender(registerMemberDto.gender())
                                   .introduction(registerMemberDto.introduction())
                                   .nickName(registerMemberDto.nickName())
                                   .profileImgUrl(registerMemberDto.profileImgUrl())
                                   .year(registerMemberDto.year())
                                   .build();

        //멤버 세팅 등록
        MemberSetting memberSetting = registerMemberSetting(member.getId(),
            registerMemberDto.memberSettingDto());

        //추가정보 등록
        MemberInfo memberInfo = registerAdditionalInfo(member, memberSetting, additionalInfo, profileImg);

        //회원가입시 사용한 정보를 통해 로그인 요청
        RequestLoginDto loginDto = RequestLoginDto.builder()
                                                  .email(registerMemberDto.email())
                                                  .password(registerMemberDto.password())
                                                  .build();
    }

    /**
     * RequestRegisterDto를 통해 새로운 멤버를 생성
     * 사전조건
     * 1. 이메일 중복 체크
     * 2. 이메일 인증
     *
     * @param registerDto
     */
    @Transactional
    protected Member registerMember(RequestRegisterDto registerDto) {
        MemberBuilder memberBuilder = Member.builder()
                                     .memberId(UUID.randomUUID()
                                                   .toString())
                                     .email(registerDto.email())
                                     .socialType(registerDto.socialType());
        String password = registerDto.password();
        //password가 null이 아니면 추가 (소셜 로그인인 경우 추가하지 않기 위해)
        if (password != null) {
            memberBuilder.password(passwordEncoder.encode(password));
        }
        Member member = memberBuilder.build();
        return memberRepository.save(member);
    }

    /**
     * 멤버 추가 정보 저장
     *
     * @param additionalInfo
     * @param profileImg
     * @return
     */
    @Transactional
    protected MemberInfo registerAdditionalInfo(Member member, MemberSetting memberSetting,
        RequestAdditionalInfo additionalInfo, MultipartFile profileImg) {
        log.info(additionalInfo.memberId());

        String profileImgUrl = additionalInfo.profileImgUrl();

        //기본 프로필 이미지 or 소셜 로그인의 경우 profileImgUrl
        String imgUrl = profileImgUrl == null ? DEFAULT_IMG_URL : profileImgUrl;
        if (profileImg != null) {
            imgUrl = commonServiceClient.saveProfileImg(profileImg, BUCKET);
        }
        MemberInfo memberInfo = MemberInfo.builder()
                                          .id(additionalInfo.id())
                                          .member(member)
                                          .memberSetting(memberSetting)
                                          .memberId(additionalInfo.memberId())
                                          .gender(additionalInfo.gender())
                                          .nickName(additionalInfo.nickName())
                                          .year(additionalInfo.year())
                                          .introduction(additionalInfo.introduction())
                                          .profileImgUrl(imgUrl)
                                          .build();

        //추가 정보 저장
        MemberInfo info = memberInfoRepository.save(memberInfo);
        //선호 카테고리 추가
        saveCategories(additionalInfo, info);
        //기본 서재 생성
        createLibrary(member.getId());
        return info;
    }

    /**
     * 멤버 세팅 저장
     *
     * @param memberId
     * @param memberSettingDto
     * @return
     */
    @Transactional
    protected MemberSetting registerMemberSetting(Long memberId, RequestMemberSettingDto memberSettingDto) {
        MemberSetting memberSetting = MemberSetting.builder()
                                                   .id(memberId)
                                                   .isLetterReceive(
                                                       memberSettingDto.isLetterReceive())
                                                   .reviewVisibility(
                                                       memberSettingDto.reviewVisibility())
                                                   .build();
        return memberSettingRepository.save(memberSetting);
    }

    /**
     * 선호 카테고리를 저장하는 메서드
     * @param requestAdditionalInfo
     * @param info
     */
    @Transactional
    protected void saveCategories(RequestAdditionalInfo requestAdditionalInfo, MemberInfo info) {
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
                  MemberCategoryMapper categoryMapper = memberCategoryMapperRepository.save(
                      memberCategoryMapper);
                  //멤버 정보에 카테고리 연관관계 추가
                  info.addCategory(categoryMapper);
              });
    }

    private void createLibrary(Long memberId) {
        RequestCreateLibraryDto createLibraryDto
            = RequestCreateLibraryDto.builder()
                                     .name("기본 서재")
                                     .libraryOrder(1)
                                     .libraryStyleDto(LibraryStyleDto.builder()
                                                                     .libraryColor("#FFFFFF")
                                                                     .build())
                                     .build();
        libraryServiceClient.createLibrary(memberId, createLibraryDto);
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

    @Override
    public ResponseLoginTokenDto registerLogin(RequestLoginDto requestLoginDto) {
        return authServiceClient.login(requestLoginDto);
    }

}
