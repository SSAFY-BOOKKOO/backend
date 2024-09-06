package com.ssafy.bookkoo.authservice.dto.OAuth;

import com.ssafy.bookkoo.authservice.entity.SocialType;
import java.util.Map;

public class NaverUserInfo extends OAuth2UserInfo {

    public NaverUserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public SocialType getSocial() {
        return SocialType.naver;
    }

    @Override
    public String getEmail() {
        return (String) ((Map) attributes.get("response")).get("email");
    }

    @Override
    public String getName() {
        return (String) ((Map) attributes.get("response")).get("name");
    }

    @Override
    public String getProfileImgUrl() {
        return (String) ((Map) attributes.get("response")).get("profile_image");
    }
}
