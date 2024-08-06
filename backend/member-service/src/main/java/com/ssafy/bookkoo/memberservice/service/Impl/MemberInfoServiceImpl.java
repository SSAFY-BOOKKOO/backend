package com.ssafy.bookkoo.memberservice.service.Impl;

import com.ssafy.bookkoo.memberservice.client.CommonServiceClient;
import com.ssafy.bookkoo.memberservice.dto.request.RequestMemberSettingDto;
import com.ssafy.bookkoo.memberservice.dto.request.RequestUpdateMemberInfoDto;
import com.ssafy.bookkoo.memberservice.dto.request.RequestUpdatePasswordDto;
import com.ssafy.bookkoo.memberservice.dto.response.ResponseMemberInfoDto;
import com.ssafy.bookkoo.memberservice.dto.response.ResponseMemberProfileDto;
import com.ssafy.bookkoo.memberservice.entity.Member;
import com.ssafy.bookkoo.memberservice.entity.MemberInfo;
import com.ssafy.bookkoo.memberservice.entity.MemberSetting;
import com.ssafy.bookkoo.memberservice.exception.MemberInfoNotExistException;
import com.ssafy.bookkoo.memberservice.exception.MemberNotFoundException;
import com.ssafy.bookkoo.memberservice.mapper.MemberInfoMapper;
import com.ssafy.bookkoo.memberservice.repository.MemberInfoRepository;
import com.ssafy.bookkoo.memberservice.repository.MemberRepository;
import com.ssafy.bookkoo.memberservice.repository.MemberSettingRepository;
import com.ssafy.bookkoo.memberservice.service.MemberInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberInfoServiceImpl implements MemberInfoService {

    private final MemberInfoRepository memberInfoRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberInfoMapper memberInfoMapper;
    private final CommonServiceClient commonServiceClient;
    private final MemberSettingRepository memberSettingRepository;

    /**
     * 비밀번호를 업데이트합니다.
     * @param requestUpdatePasswordDto
     */
    @Override
    public void updatePassword(Long memberId, RequestUpdatePasswordDto requestUpdatePasswordDto) {
        Member member = memberRepository.findById(memberId)
                                        .orElseThrow(MemberNotFoundException::new);

        member.setPassword(passwordEncoder.encode(requestUpdatePasswordDto.password()));

        memberRepository.save(member);
        memberRepository.flush();
    }

    /**
     * 멤버 ID(UUID)를 통해 멤버 정보를 반환합니다. (마이페이지에 보여줄 내용만 반환)
     *
     * @param memberId
     * @return
     */
    @Override
    public ResponseMemberProfileDto getMemberProfileInfo(String memberId) {
        MemberInfo memberInfo = memberInfoRepository.findByMemberId(memberId)
                                                    .orElseThrow(MemberNotFoundException::new);
        String email = memberInfo.getMember()
                                 .getEmail();
        return memberInfoMapper.toResponseProfileDto(email, memberInfo);
    }

    @Override
    public ResponseMemberProfileDto getMemberProfileInfo(Long id) {
        MemberInfo memberInfo = memberInfoRepository.findById(id)
                                                    .orElseThrow(MemberNotFoundException::new);
        String email = memberInfo.getMember()
                                 .getEmail();
        return memberInfoMapper.toResponseProfileDto(email, memberInfo);
    }

    /**
     * 멤버 ID(Long)을 통해 멤버 정보를 반환합니다.
     * 멤버 정보 전체를 반환합니다.
     *
     * @param memberId
     * @return
     */
    @Override
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
    public List<Long> getRandomMemberInfo(List<Long> followers) {
        return memberInfoRepository.findRandomMemberInfoIdByFollowers(followers);
    }

    /**
     * 닉네임을 통해 memberId(Long)을 반환합니다.
     * @param nickName
     * @return
     */
    @Override
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
        memberSettingRepository.save(memberSetting);
        memberSettingRepository.flush();
    }

    /**
     * 멤버 추가 정보를 변경하는 서비스 로직
     * @param id
     * @param memberInfoUpdateDto
     * @param profileImg
     */
    @Override
    @Transactional
    public void updateMemberInfo(Long id, RequestUpdateMemberInfoDto memberInfoUpdateDto,
        MultipartFile profileImg) {
        MemberInfo memberInfo = memberInfoRepository.findById(id)
                                                    .orElseThrow(MemberInfoNotExistException::new);
        //TODO: 닉네임 중복 체크 필요
        memberInfo.setNickName(memberInfoUpdateDto.nickName());
        if (profileImg != null) {
            updateProfileImg(id, profileImg);
        }
        memberInfo.setIntroduction(memberInfoUpdateDto.introduction());
        memberInfoRepository.save(memberInfo);
        memberInfoRepository.flush();
    }

    /**
     * 새로운 이미지를 버킷에 저장하고 기본 이미지가 아니면 버킷에서 삭제하고 새로운 이미지 저장 멤버 정보의 프로필 사진 정보 수정
     *
     * @param id
     * @param profileImg
     */
    public void updateProfileImg(Long id, MultipartFile profileImg) {
        MemberInfo memberInfo = memberInfoRepository.findById(id)
                                                    .orElseThrow(MemberNotFoundException::new);
        String profileImgUrl = memberInfo.getProfileImgUrl();
        if (!profileImgUrl.equals("Default.jpg")) {
            commonServiceClient.deleteProfileImg(profileImgUrl, null);
        }
        String fileName = commonServiceClient.saveProfileImg(profileImg, null);
        memberInfo.setProfileImgUrl(fileName);
        memberInfoRepository.save(memberInfo);
        memberInfoRepository.flush();
    }

}
