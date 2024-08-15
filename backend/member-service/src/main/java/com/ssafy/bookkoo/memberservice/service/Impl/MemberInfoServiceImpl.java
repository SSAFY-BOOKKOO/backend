package com.ssafy.bookkoo.memberservice.service.Impl;

import com.ssafy.bookkoo.memberservice.client.BookServiceClient;
import com.ssafy.bookkoo.memberservice.client.CommonServiceClient;
import com.ssafy.bookkoo.memberservice.client.LibraryServiceClient;
import com.ssafy.bookkoo.memberservice.dto.request.RequestMemberSettingDto;
import com.ssafy.bookkoo.memberservice.dto.request.RequestUpdateMemberInfoDto;
import com.ssafy.bookkoo.memberservice.dto.request.RequestUpdatePasswordDto;
import com.ssafy.bookkoo.memberservice.dto.response.ResponseFindMemberProfileDto;
import com.ssafy.bookkoo.memberservice.dto.response.ResponseMemberInfoDto;
import com.ssafy.bookkoo.memberservice.dto.response.ResponseMemberProfileDto;
import com.ssafy.bookkoo.memberservice.dto.response.ResponseRecipientDto;
import com.ssafy.bookkoo.memberservice.entity.*;
import com.ssafy.bookkoo.memberservice.exception.DeleteFailException;
import com.ssafy.bookkoo.memberservice.exception.MemberInfoNotExistException;
import com.ssafy.bookkoo.memberservice.exception.MemberNotFoundException;
import com.ssafy.bookkoo.memberservice.exception.ImageUploadException;
import com.ssafy.bookkoo.memberservice.mapper.MemberInfoMapper;
import com.ssafy.bookkoo.memberservice.repository.MemberCategoryMapperRepository;
import com.ssafy.bookkoo.memberservice.repository.MemberInfoRepository;
import com.ssafy.bookkoo.memberservice.repository.MemberRepository;
import com.ssafy.bookkoo.memberservice.repository.MemberSettingRepository;
import com.ssafy.bookkoo.memberservice.service.MemberInfoService;
import com.ssafy.bookkoo.memberservice.service.MemberService;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MemberInfoServiceImpl implements MemberInfoService {

    private final MemberInfoRepository memberInfoRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberInfoMapper memberInfoMapper;
    private final CommonServiceClient commonServiceClient;
    private final MemberSettingRepository memberSettingRepository;
    private final MemberCategoryMapperRepository memberCategoryMapperRepository;
    private final MemberService memberService;
    private final LibraryServiceClient libraryServiceClient;
    private final BookServiceClient bookServiceClient;

    @Value("${config.member-bucket-name}")
    private String BUCKET;

    @Value("${config.server-url}")
    private String SERVER;

    @Value("${config.common-service-file}")
    private String COMMON_URL;

    /**
     * 비밀번호를 업데이트합니다.
     * @param requestUpdatePasswordDto
     */
    @Override
    @Transactional
    public void updatePassword(Long memberId, RequestUpdatePasswordDto requestUpdatePasswordDto) {
        Member member = memberRepository.findById(memberId)
                                        .orElseThrow(MemberNotFoundException::new);

        member.setPassword(passwordEncoder.encode(requestUpdatePasswordDto.password()));

        memberRepository.flush();
    }

    /**
     * 멤버 ID(UUID)를 통해 멤버 정보를 반환합니다. (마이페이지에 보여줄 내용만 반환)
     *
     * @param memberId
     * @return
     */
    @Override
    @Cacheable(value = "other_member_profile_info", key = "#memberId")
    @Transactional(readOnly = true)
    public ResponseMemberProfileDto getMemberProfileInfo(String memberId) {
        MemberInfo memberInfo = memberInfoRepository.findByMemberId(memberId)
                                                    .orElseThrow(MemberNotFoundException::new);
        String email = memberInfo.getMember()
                                 .getEmail();
        return memberInfoMapper.toResponseProfileDto(email, memberInfo);
    }

    /**
     * Long을 통해 반환
     * @param memberId
     * @return
     */
    @Override
    @Cacheable(value = "member_profile_info", key = "#memberId")
    @Transactional(readOnly = true)
    public ResponseMemberProfileDto getMemberProfileInfo(Long memberId) {
        MemberInfo memberInfo = memberInfoRepository.findById(memberId)
                                                    .orElseThrow(MemberNotFoundException::new);
        String email = memberInfo.getMember()
                                 .getEmail();
        return memberInfoMapper.toResponseProfileDto(email, memberInfo);
    }

    /**
     * 닉네임을 통해 마이페이지의 정보를 반환
     * @param nickName
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseMemberProfileDto getMemberProfileInfoByNickName(String nickName) {
        MemberInfo memberInfo = memberInfoRepository.findByNickName(nickName)
                                                    .orElseThrow(MemberNotFoundException::new);

        String email = memberInfo.getMember()
                                 .getEmail();
        return memberInfoMapper.toResponseProfileDto(email, memberInfo);
    }

    /**
     * Like를 통해 닉네임으로 멤버 정보 찾기 여러명 반환
     * 자신 제외 및 팔로우 여부 필드 추가 반환
     * @param memberId
     * @param nickName
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<ResponseFindMemberProfileDto> getMemberProfileListInfoByNickName(Long memberId, String nickName) {
        MemberInfo member = memberInfoRepository.findById(memberId)
                                                .orElseThrow(MemberInfoNotExistException::new);
        List<FollowShip> followees = member.getFollowees();
        List<MemberInfo> memberInfos = memberInfoRepository.findTOP10ByNickNameContainsAndIdNe(memberId, nickName);
        return memberInfos.stream()
                          .map(memberInfo -> {
                              String email = memberInfo.getMember()
                                                       .getEmail();
                              boolean isFollow = false;
                              for (FollowShip followShip : followees) {
                                  Long followeeId = followShip.getFollowee()
                                                              .getId();
                                  if (followeeId.equals(memberInfo.getId())) {
                                      isFollow = true;
                                      break;
                                  }
                              }

                              return memberInfoMapper.toResponseFindProfileDto(email, isFollow, memberInfo);
                          })
                          .collect(Collectors.toList());
    }

    /**
     * 멤버 ID(Long)을 통해 멤버 정보를 반환합니다.
     * 멤버 정보 전체를 반환합니다.
     *
     * @param memberId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseMemberInfoDto getMemberInfo(Long memberId) {
        MemberInfo memberInfo = memberInfoRepository.findById(memberId)
                                                    .orElseThrow(MemberNotFoundException::new);

        return memberInfoMapper.toResponseDto(memberInfo);
    }

    /**
     * 내부적으로 사용하기 위한 서비스
     * 멤버 ID를 통해  PK(Long)을 반환하는 서비스
     * @param memberId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Long getMemberPk(String memberId) {
        return memberInfoRepository.findByMemberId(memberId)
                                   .orElseThrow(MemberNotFoundException::new)
                                   .getId();
    }

    /**
     * @param followers
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<Long> getRandomMemberInfoId(List<Long> followers) {
        return memberInfoRepository.findRandomMemberInfoIdByFollowers(followers);
    }

    /**
     * 닉네임을 통해 memberId(Long)을 반환합니다.
     * @param nickName
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Long getMemberIdByNickName(String nickName) {
        return memberInfoRepository.findByNickName(nickName)
                                   .orElseThrow(MemberNotFoundException::new)
                                   .getId();
    }

    /**
     * 멤버의 공개 범위 설정 변경을 위한 서비스 로직
     * @param id
     * @param memberSettingDto
     */
    @Override
    @Transactional
    public void updateMemberSetting(Long id, RequestMemberSettingDto memberSettingDto) {
        MemberSetting memberSetting = memberSettingRepository.findById(id)
                                                             .orElseThrow(
                                                                 MemberNotFoundException::new);
        memberSetting.setIsLetterReceive(memberSettingDto.isLetterReceive());
        memberSetting.setReviewVisibility(memberSettingDto.reviewVisibility());
        memberSettingRepository.flush();
    }

    /**
     * 멤버 추가 정보를 변경하는 서비스 로직
     *
     * @param memberId
     * @param memberInfoUpdateDto
     * @param profileImg
     * @return
     */
    @Override
    @Transactional
    @CacheEvict(value = "member_profile_info", key = "#memberId")
    @CachePut(value = "member_profile_info", key = "#memberId")
    public ResponseMemberProfileDto updateMemberInfo(
        Long memberId, RequestUpdateMemberInfoDto memberInfoUpdateDto,
        MultipartFile profileImg) {
        MemberInfo memberInfo = memberInfoRepository.findById(memberId)
                                                    .orElseThrow(MemberInfoNotExistException::new);
        checkDuplNickName(memberInfo.getNickName(), memberInfoUpdateDto.nickName());
        memberInfo.setNickName(memberInfoUpdateDto.nickName());
        if (profileImg != null) {
            updateProfileImg(memberId, profileImg);
        }
        updateCategories(memberInfo, memberInfoUpdateDto.categories());
        memberInfo.setIntroduction(memberInfoUpdateDto.introduction());
        memberInfoRepository.flush();
        String email = memberInfo.getMember()
                                 .getEmail();
        return memberInfoMapper.toResponseProfileDto(email, memberInfo);
    }

    /**
     * 큐레이션 레터의 수신자들의 정보 (ID, email, 이메일 수신 여부 반환)
     * @param recipientIds
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<ResponseRecipientDto> getRecipientsInfo(List<Long> recipientIds) {
        return memberInfoRepository.findByRecipientsInfoByIds(recipientIds);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberInfo getMemberInfoEntity(Long memberId) {
        return memberInfoRepository.findById(memberId)
                                   .orElseThrow(MemberInfoNotExistException::new);
    }


    /**
     * 회원 탈퇴를 수행합니다.
     * @param memberId
     */
    @Override
    @Transactional
    public void deleteMemberHistory(Long memberId) {
        try {
            libraryServiceClient.deleteLibraries(memberId);
            bookServiceClient.deleteMyReview(memberId);
        } catch (Exception e) {
            throw new DeleteFailException();
        }
        //클래스 내부 호출은 트랜잭션이 적용되지 않으므로 AopContext를 통해 프록시를 통해 메서드 호출
        ((MemberInfoServiceImpl) AopContext.currentProxy()).deleteMember(memberId);
    }

    @Transactional
    public void deleteMember(Long memberId) {
        memberInfoRepository.deleteById(memberId);
    }


    /**
     * 이전 닉네임과 같으면 중복체크 X
     * 다르면 중복 체크
     * @param oldNickName
     * @param newNickName
     */
    private void checkDuplNickName(String oldNickName, String newNickName) {
        if (!oldNickName.equals(newNickName)) {
            memberService.checkDuplNickName(newNickName);
        }
    }

    /**
     * 전체 카테고리 삭제 및 새로운 카테고리 저장
     * @param memberInfo
     * @param categories
     */
    @Transactional
    protected void updateCategories(MemberInfo memberInfo, Integer[] categories) {
        deleteCategories(memberInfo);
        saveCategories(memberInfo, categories);
    }

    /**
     * 전체 카테고리 삭제
     * @param memberInfo
     */
    @Transactional
    protected void deleteCategories(MemberInfo memberInfo) {
        memberCategoryMapperRepository.deleteAll(memberInfo.getCategories());
    }


    /**
     * 입력으로 들어온 카테고리를 저장
     * @param memberInfo
     * @param categories
     */
    @Transactional
    protected void saveCategories(MemberInfo memberInfo, Integer[] categories) {
        Arrays.stream(categories)
              .forEach((categoryId) -> {
                  //멤버 매퍼 키를 생성
                  MemberCategoryMapperKey memberCategoryMapperKey
                      = MemberCategoryMapperKey.builder()
                                               .categoryId(categoryId)
                                               .memberInfoId(memberInfo.getId())
                                               .build();
                  //매퍼키를 통해 매퍼 엔티티 생성
                  MemberCategoryMapper memberCategoryMapper
                      = MemberCategoryMapper.builder()
                                            .memberCategoryMapperKey(
                                                memberCategoryMapperKey)
                                            .memberInfo(memberInfo)
                                            .build();
                  //매퍼 테이블에 매퍼 정보 저장
                  MemberCategoryMapper categoryMapper = memberCategoryMapperRepository.save(
                      memberCategoryMapper);
                  //멤버 정보에 카테고리 연관관계 추가
                  memberInfo.addCategory(categoryMapper);
              });
    }

    /**
     * 새로운 이미지를 버킷에 저장하고 기본 이미지가 아니면 버킷에서 삭제하고 새로운 이미지 저장 멤버 정보의 프로필 사진 정보 수정
     *
     * @param id
     * @param profileImg
     */
    @Transactional
    public void updateProfileImg(Long id, MultipartFile profileImg) {
        MemberInfo memberInfo = memberInfoRepository.findById(id)
                                                    .orElseThrow(MemberNotFoundException::new);
        String profileImgUrl = memberInfo.getProfileImgUrl();
        if (!profileImgUrl.equals("Default.jpg")) {
            try {
                commonServiceClient.deleteImg(profileImgUrl, BUCKET);
            } catch (Exception e) {
                throw new ImageUploadException();
            }
        }
        updateMemberProfileUrl(profileImg, memberInfo);
    }

    protected void updateMemberProfileUrl(MultipartFile profileImg, MemberInfo memberInfo) {
        String imgUrl = null;
        try {
            imgUrl = commonServiceClient.saveImg(profileImg, BUCKET);
        } catch (Exception e) {
            throw new ImageUploadException();
        }
        imgUrl = SERVER + COMMON_URL + imgUrl;
        memberInfo.setProfileImgUrl(imgUrl);
        memberInfoRepository.flush();
    }

}
