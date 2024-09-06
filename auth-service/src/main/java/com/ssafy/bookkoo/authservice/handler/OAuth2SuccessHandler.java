package com.ssafy.bookkoo.authservice.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.bookkoo.authservice.dto.ResponseLoginTokenDto;
import com.ssafy.bookkoo.authservice.dto.ResponseSocialRegisterDto;
import com.ssafy.bookkoo.authservice.dto.SocialRegisterDto;
import com.ssafy.bookkoo.authservice.entity.Member;
import com.ssafy.bookkoo.authservice.service.AuthService;
import com.ssafy.bookkoo.authservice.util.CookieUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

/**
 * OAuth2 인증 완료 시 동작하는 SuccessHandler
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final String REFRESH_TOKEN_NAME = "refresh_token";
    private final AuthService authService;
    private final ObjectMapper objectMapper;

    public final static String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";

    @Value("${config.frontend}")
    private String FRONTEND_URL;

    /**
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        //이메일
        String email = authentication.getName();

        log.info("authentication : {}", authentication);
        log.info("authentication principal: {}", authentication.getPrincipal());
        Object principal = authentication.getPrincipal();

        CookieUtils.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
        // ResponseSocialRegisterDto이면 ROLE_GUEST
        if (principal instanceof SocialRegisterDto socialRegisterDto) {
            ResponseSocialRegisterDto registerDto
                = toResponseSocialRegisterDto(socialRegisterDto);
            log.info("socialRegisterDto : {}", socialRegisterDto);
            //소셜 로그인 정보를 클라이언트로 전송하고 추가정보를 입력받아 회원가입 완료
            String targetURl = UriComponentsBuilder.fromUriString(FRONTEND_URL + "/register")
                                                   .queryParam("email", registerDto.email())
                                                   .queryParam("socialType", registerDto.socialType())
                                                   .build()
                                                   .toUriString();
            getRedirectStrategy().sendRedirect(request, response, targetURl);
        } else { //Member이면 ROLE_USER (로그인 처리 토큰 전송)
            Member member = (Member) principal;
            log.info("Member : {}", member);
            ResponseLoginTokenDto tokenDto = authService.login(member.getEmail());
            Cookie cookie = CookieUtils.secureCookieGenerate(REFRESH_TOKEN_NAME,
                tokenDto.refreshToken(), CookieUtils.REFRESH_TOKEN_EXPIRATION);
//            cookie.setDomain("ssafy.io");
            response.addCookie(cookie);
            //토큰 관련 정보를 추가하여 로그인 처리
            String targetURl = UriComponentsBuilder.fromUriString(FRONTEND_URL + "/auth/callback")
                                                   .queryParam("token", tokenDto.accessToken())
                                                   .build()
                                                   .toUriString();
            getRedirectStrategy().sendRedirect(request, response, targetURl);
        }
    }

    /**
     * 클라이언트로 반환하기 위해 소셜 멤버 정보를 컨버팅
     *
     * @param socialRegisterDto
     * @return
     */
    public ResponseSocialRegisterDto toResponseSocialRegisterDto(
        SocialRegisterDto socialRegisterDto) {
        StringBuilder nickName = new StringBuilder();
        nickName.append(socialRegisterDto.getSocialType());
        nickName.append("_");
        nickName.append(socialRegisterDto.getEmail());
        return ResponseSocialRegisterDto.builder()
                                        .email(socialRegisterDto.getEmail())
                                        .nickName(nickName.toString())
                                        .socialType(socialRegisterDto.getSocialType())
                                        .profileImgUrl(socialRegisterDto.getProfileImgUrl())
                                        .build();
    }
}
