package com.ssafy.bookkoo.authservice.config.filter;

import com.ssafy.bookkoo.authservice.util.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 필터 로직
     * 토큰의 유효성 검사
     * TODO: 
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        //헤더에서 Authorization 뽑기
        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
        //Authorization에서 Bearer를 통해 액세스 토큰 뽑기
        String accessToken = getAccessToken(authorizationHeader);
        if (tokenProvider.validToken(accessToken)) {
            //액세스 토큰을 SecurityContextHolder에 세팅해주기
            //TODO: 현재 서비스에서 는 사용하지 않으므로 필요 없음
//            Authentication authentication = tokenProvider.getAuthentication(accessToken);
//            SecurityContextHolder.getContext()
//                                 .setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Bearer를 뺀 나머지 (액세스 토큰)
     * @param authorizationHeader
     * @return
     */
    private String getAccessToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}
