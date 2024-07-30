package com.ssafy.bookkoo.authservice.dto.OAuth;

import com.ssafy.bookkoo.authservice.entity.SocialType;
import java.util.Map;

public abstract class OAuth2UserInfo {

    Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract SocialType getSocial();

    public abstract String getEmail();

    public abstract String getName();

    public abstract String getProfileImgUrl();
}
