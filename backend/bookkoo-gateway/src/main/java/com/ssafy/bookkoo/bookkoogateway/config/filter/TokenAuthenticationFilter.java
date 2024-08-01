package com.ssafy.bookkoo.bookkoogateway.config.filter;

import com.ssafy.bookkoo.bookkoogateway.client.MemberServiceWebClient;
import com.ssafy.bookkoo.bookkoogateway.exception.TokenExpirationException;
import com.ssafy.bookkoo.bookkoogateway.util.TokenUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
/**
 * 글로벌 필터 (컴포넌트 등록 시 자동으로 해당 필터를 통과하도록 설정된다.)
 */
public class TokenAuthenticationFilter implements GlobalFilter {

    private static final String PASSPORT_HEADER = "Member-Passport";
    private static final String TOKEN_PREFIX = "Bearer ";
    private final TokenUtils tokenUtils;
    private final MemberServiceWebClient memberServiceWebClient;
    private final List<String> excludedPaths = List.of(
        //swagger 요청 관련 prefix
        "/auth-service",
        "/book-service",
        "/common-service",
        "/curation-service",
        "/library-service",
        "/member-service",
        "/api-docs",
        "/swagger-ui.html",
        "/swagger-ui",
        "/docs", "/webjars", "/v3",
        //정적 리소스
        "/js",
        "/css",
        "/images",
        //토큰 인증 스킵 URL
        "/members/register",
        "/auth",
        "/actuator",
        "/categories",
        "/books"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("토큰 인증 필터 진입");
        //헤더에서 Authorization 뽑기
        String authorizationHeader = exchange.getRequest()
                                             .getHeaders()
                                             .getFirst(HttpHeaders.AUTHORIZATION);
        //Authorization에서 Bearer를 통해 액세스 토큰 뽑기
        String accessToken = getAccessToken(authorizationHeader);

        String path = exchange.getRequest()
                              .getURI()
                              .getPath();

        //토큰 인증을 거치지 않는 요청URL에 해당되면 토큰 검증 스킵
        for (String excludedPath : excludedPaths) {
            if (path.startsWith(excludedPath)) {
                // /books/{bookId}/reviews~ 경로는 필터링 필요
                if (excludedPath.equals("/books") && path.matches("^/books/\\d+/reviews(/.*)?$")) {
                    break; // 필터를 거쳐야 함
                }
                return chain.filter(exchange);
            }
        }
        if (path.matches(".*/favicon\\..*")) {
            return chain.filter(exchange);
        }

        if (accessToken == null || !tokenUtils.validToken(accessToken)) {
            log.info("토큰이 만료되었습니다.");
            throw new TokenExpirationException();
        }

        Authentication authentication = tokenUtils.getAuthentication(accessToken);
        String subject = tokenUtils.getSubject(accessToken);
        SecurityContextHolder.getContext()
                             .setAuthentication(authentication);

        return memberServiceWebClient.getMemberId(subject)
                                     .flatMap(id -> {
                                         //Passpord 정보를 담은 헤더를 세팅
                                         ServerHttpRequest mutatedRequest = exchange.getRequest()
                                                                                    .mutate()
                                                                                    .header(
                                                                                        PASSPORT_HEADER,
                                                                                        String.valueOf(
                                                                                            id))
                                                                                    .build();
                                         //헤더 정보를 담은 Request를 추가한 exchange를 다음 필터로 전달
                                         return chain.filter(exchange.mutate()
                                                                     .request(mutatedRequest)
                                                                     .build())
                                                     .contextWrite(
                                                         ReactiveSecurityContextHolder.withAuthentication(
                                                             authentication));
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