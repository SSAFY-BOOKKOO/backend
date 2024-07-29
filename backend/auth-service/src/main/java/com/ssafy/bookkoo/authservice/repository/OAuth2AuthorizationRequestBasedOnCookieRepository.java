package com.ssafy.bookkoo.authservice.repository;

import com.ssafy.bookkoo.authservice.util.CookieUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;import java.time.Duration;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

@Component
public class OAuth2AuthorizationRequestBasedOnCookieRepository implements
    AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    // 쿠키 키
    public final static String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
    // 쿠키 수명
    private final static Duration COOKIE_EXPIRE_SECONDS = Duration.ofSeconds(18000);



    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
        return CookieUtils.deserialize(cookie, OAuth2AuthorizationRequest.class);
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest,
        HttpServletRequest request, HttpServletResponse response) {
        if (authorizationRequest == null) {
            removeAuthorizationRequestCookies(request, response);
            return;
        }
        Cookie cookie = CookieUtils.secureCookieGenerate(OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME,
            CookieUtils.serialize(authorizationRequest), COOKIE_EXPIRE_SECONDS);
        response.addCookie(cookie);
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request,
        HttpServletResponse response) {
        return this.loadAuthorizationRequest(request);
    }

    public void removeAuthorizationRequestCookies(HttpServletRequest request,
        HttpServletResponse response) {
        CookieUtils.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
    }

}
