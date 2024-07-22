package com.ssafy.bookkoo.bookkoogateway.config.filter;

import com.ssafy.bookkoo.bookkoogateway.util.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter implements WebFilter {

    private static final String TOKEN_PREFIX = "Bearer ";
    private final TokenUtils tokenUtils;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        //헤더에서 Authorization 뽑기
        String authorizationHeader = exchange.getRequest()
                                             .getHeaders()
                                             .getFirst(HttpHeaders.AUTHORIZATION);

        //Authorization에서 Bearer를 통해 액세스 토큰 뽑기
        String accessToken = getAccessToken(authorizationHeader);

        if (accessToken != null && tokenUtils.validToken(accessToken)) {
            Authentication authentication = tokenUtils.getAuthentication(accessToken);
            SecurityContextHolder.getContext()
                                 .setAuthentication(authentication);
        }
        return chain.filter(exchange)
                    .contextWrite(ReactiveSecurityContextHolder.withAuthentication(
                        SecurityContextHolder.getContext()
                                             .getAuthentication()));
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
