package com.ssafy.bookkoo.authservice.dto;

import com.ssafy.bookkoo.authservice.entity.SocialType;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Data
public class SocialRegisterDto implements OAuth2User {

    private String email;

    private SocialType socialType;

    private String profileImgUrl;

    private Map<String, Object> attributes;


    @Builder
    public SocialRegisterDto(String email, SocialType socialType, String profileImgUrl,
        Map<String, Object> attributes) {
        this.email = email;
        this.socialType = socialType;
        this.profileImgUrl = profileImgUrl;
        this.attributes = attributes;
    }



    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_GUEST"));
    }

    @Override
    public String getName() {
        return this.email;
    }
}
