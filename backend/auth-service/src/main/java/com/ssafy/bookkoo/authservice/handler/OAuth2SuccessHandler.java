package com.ssafy.bookkoo.authservice.handler;

import com.ssafy.bookkoo.authservice.client.MemberServiceClient;
import com.ssafy.bookkoo.authservice.entity.Member;
import com.ssafy.bookkoo.authservice.entity.SocialType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * OAuth2 인증 완료 시 동작하는 SuccessHandler
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final MemberServiceClient memberServiceClient;

    @Value("${config.frontend}")
    private String FRONTEND_URL;

    /**
     *
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

        Authentication authentication1 = SecurityContextHolder.getContext()
                                                              .getAuthentication();
        //TODO: 형변환되는지 확인해보고 이를 활용하도록 하기
        //TODO: 가능하다면 ResponseBody를 통해 OAuth2 멤버정보를 클라이언트로 반환하도록하기
        Member member = (Member) authentication.getPrincipal();
        response.sendRedirect(FRONTEND_URL + "/library");

    }
}
