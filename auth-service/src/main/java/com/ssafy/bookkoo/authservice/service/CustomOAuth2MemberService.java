package com.ssafy.bookkoo.authservice.service;

import com.ssafy.bookkoo.authservice.dto.OAuth.GoogleUserInfo;
import com.ssafy.bookkoo.authservice.dto.OAuth.KakaoUserInfo;
import com.ssafy.bookkoo.authservice.dto.OAuth.NaverUserInfo;
import com.ssafy.bookkoo.authservice.dto.OAuth.OAuth2UserInfo;
import com.ssafy.bookkoo.authservice.dto.SocialRegisterDto;
import com.ssafy.bookkoo.authservice.entity.Member;
import com.ssafy.bookkoo.authservice.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2MemberService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    /**
     * 요청을 통해 받은 UserRequest를 통해 유저 정보를 받아오는 메서드
     * @param userRequest
     * @return
     * @throws OAuth2AuthenticationException
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        //제공한 권한 서버의 ID (google, kakao, naver)
        String registrationId = userRequest.getClientRegistration()
                                           .getRegistrationId();
        OAuth2UserInfo oAuth2UserInfo = null;
        //Provider들 각각이 key를 다르게 가지고 있음 필요한 것을 추상 클래스를 통해 공통으로 꺼내올 수 있도록 처리
        switch (registrationId) {
            case "google":
                oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
                break;
            case "kakao":
                oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
                break;
            case "naver":
                oAuth2UserInfo = new NaverUserInfo(oAuth2User.getAttributes());
                break;
            default:
                throw new OAuth2AuthenticationException("Provider Not Valid");
        }

        String email = oAuth2UserInfo.getEmail();

        OAuth2User user = SocialRegisterDto.builder()
                                           .attributes(oAuth2User.getAttributes())
                                           .email(email)
                                           .socialType(oAuth2UserInfo.getSocial())
                                           .profileImgUrl(oAuth2UserInfo.getProfileImgUrl())
                                           .build();

        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if (optionalMember.isPresent()) {
            user = optionalMember.get();
        }

        return user;
    }
}
