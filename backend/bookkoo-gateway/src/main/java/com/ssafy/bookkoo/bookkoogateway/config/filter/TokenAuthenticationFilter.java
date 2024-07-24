package com.ssafy.bookkoo.bookkoogateway.config.filter;

import com.ssafy.bookkoo.bookkoogateway.client.MemberServiceWebClient;
import com.ssafy.bookkoo.bookkoogateway.exception.TokenExpirationException;
import com.ssafy.bookkoo.bookkoogateway.util.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter implements WebFilter {

    private static final String PASSPORT_HEADER = "Member-Passport";
    private static final String TOKEN_PREFIX = "Bearer ";
    private final TokenUtils tokenUtils;
    private final MemberServiceWebClient memberServiceWebClient;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        log.info("토큰 인증 필터 진입");
        //헤더에서 Authorization 뽑기
        String authorizationHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        //Authorization에서 Bearer를 통해 액세스 토큰 뽑기
        String accessToken = getAccessToken(authorizationHeader);

        if(accessToken == null || !tokenUtils.validToken(accessToken)) {
            log.info("토큰이 만료되었습니다.");
            throw new TokenExpirationException();
        }

        Authentication authentication = tokenUtils.getAuthentication(accessToken);
        String subject = tokenUtils.getSubject(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return memberServiceWebClient.getMemberId(subject)
                                     .flatMap(id -> {
                                         //Passpord 정보를 담은 헤더를 세팅
                                         ServerHttpRequest mutatedRequest = exchange.getRequest()
                                                                                    .mutate()
                                                                                    .header(PASSPORT_HEADER, String.valueOf(id))
                                                                                    .build();
                                         //헤더 정보를 담은 Request를 추가한 exchange를 다음 필터로 전달
                                         return chain.filter(exchange.mutate()
                                                                     .request(mutatedRequest)
                                                                     .build())
                                                     .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
                                     })
                                     .doOnError(error -> {
                                         log.error("토큰 필터 에러 {}", error.getMessage());
                                     });
    }

    private String getAccessToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}
