package com.ssafy.bookkoo.authservice.dto.OAuth;

import com.ssafy.bookkoo.authservice.entity.SocialType;
import java.util.Map;

public class KakaoUserInfo extends OAuth2UserInfo{

    public KakaoUserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public SocialType getSocial() {
        return SocialType.kakao;
    }

    @Override
    public String getEmail() {
        return (String) ((Map) attributes.get("kakao_account")).get("email");
    }

    @Override
    public String getName() {
        return (String) ((Map) attributes.get("properties")).get("nickname");
    }

    @Override
    public String getProfileImgUrl() {
        return (String) ((Map) attributes.get("properties")).get("profile_image");
    }
}
