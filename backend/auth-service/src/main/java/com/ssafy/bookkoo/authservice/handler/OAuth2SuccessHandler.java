package com.ssafy.bookkoo.authservice.handler;

import com.ssafy.bookkoo.authservice.client.MemberServiceClient;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final MemberServiceClient memberServiceClient;

    @Value("config.frontend")
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

        //TODO: memberInfo 존재 시 로그인 토큰 처리 및 Main Page로 이동
        //TODO: 아직 입력하지 않은 경우 추가 정보 입력으로 Redirect 처리

        response.sendRedirect(FRONTEND_URL + "/library");
    }
}
