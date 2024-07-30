package com.ssafy.bookkoo.authservice.dto.OAuth;

import com.ssafy.bookkoo.authservice.entity.SocialType;
import java.util.Map;

public class GoogleUserInfo extends OAuth2UserInfo{

    public GoogleUserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public SocialType getSocial() {
        return SocialType.google;
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getProfileImgUrl() {
        return (String) attributes.get("picture");
    }

}
