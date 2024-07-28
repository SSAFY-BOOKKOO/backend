package com.ssafy.bookkoo.authservice.handler;

import com.ssafy.bookkoo.authservice.client.MemberServiceClient;
import com.ssafy.bookkoo.authservice.entity.Member;
import com.ssafy.bookkoo.authservice.repository.MemberRepository;
import com.ssafy.bookkoo.authservice.repository.OAuth2AuthorizationRequestBasedOnRepository;
import com.ssafy.bookkoo.authservice.repository.RefreshTokenRepository;
import com.ssafy.bookkoo.authservice.service.TokenService;
import com.ssafy.bookkoo.authservice.util.CookieUtils;
import com.ssafy.bookkoo.authservice.util.TokenGenerator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenGenerator tokenGenerator;
    private final RefreshTokenRepository refreshTokenRepository;
    private final OAuth2AuthorizationRequestBasedOnRepository oAuth2AuthorizationRequestBasedOnRepository;
    private final TokenService tokenService;
    private final MemberServiceClient memberServiceClient;
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        FilterChain chain, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        log.info("OAUTH Success");
        Optional<Member> optionalMember = memberRepository.findByMemberId(email);
        if (optionalMember.isEmpty()) { // 새로운 유저 (추가 정보 저장 페이지로 redirect)
            String memberId = memberServiceClient.register(email);
            log.info("google login : {}", memberId);
            response.sendRedirect("/");
        } else {
            //TODO: 아래 로직 구체화해야함.
            Member member = optionalMember.get();
            Long memberPK = memberServiceClient.getMemberPK(member.getMemberId());
            //등록은 되었지만, 추가 정보를 아직 못받은 상태
            if (memberPK == null) {
                response.sendRedirect("/");
            } else {
                //회원가입, 추가정보 등록 모두 완료 (로그인)
                //리프레시 토큰 관련 로직 수행
                String refreshToken = tokenService.updateRefreshToken(member);
                Cookie cookie = CookieUtils.secureCookieGenerate("refresh_token", refreshToken,
                    CookieUtils.REFRESH_TOKEN_EXPIRATION);
                response.addCookie(cookie);
                getRedirectStrategy().sendRedirect(request, response, "/auth/token");
            }
        }
    }

    private void clearAuthenticationAttributes(HttpServletRequest request,
        HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
    }
}
