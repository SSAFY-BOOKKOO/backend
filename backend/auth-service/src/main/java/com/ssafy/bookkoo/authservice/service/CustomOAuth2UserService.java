package com.ssafy.bookkoo.authservice.service;

import com.ssafy.bookkoo.authservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        //TODO: 각 플랫폼에 맞게 구현
        return super.loadUser(userRequest);
//        OAuth2User oAuth2User = super.loadUser(userRequest);
//
//        OAuth2UserInfo oAuth2UserInfo = null;
//
//        switch (userRequest.getClientRegistration()
//                           .getRegistrationId()) {
//            case "google":
//                oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
//                break;
//            case "kakao":
//                oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
//                break;
//            case "naver":
//                oAuth2UserInfo = new NaverUserInfo(oAuth2User.getAttributes());
//                break;
//            default:
//                throw new OAuth2AuthenticationException("Provider Not Found");
//        }

//        Member member = memberRepository.findByEmailAndSocial(oAuth2UserInfo.getEmail(),
//                                            oAuth2UserInfo.getSocial())
//                                        .orElse(Member.builder()
//                                                      .email(oAuth2UserInfo.getEmail())
//                                                      .social(oAuth2UserInfo.getSocial())
//                                                      .point(0L)
//                                                      .profileImgUrl(minioConfig.getUrl()
//                                                          + "/member/NoProfile.png")
//                                                      .build());
//
//        member.setName(oAuth2UserInfo.getName());
//
//        memberRepository.save(member);
//
//        return member;
    }
}
