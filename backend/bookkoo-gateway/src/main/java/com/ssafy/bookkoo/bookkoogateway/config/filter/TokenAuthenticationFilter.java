//package com.ssafy.bookkoo.bookkoogateway.config.filter;
//
//import com.ssafy.bookkoo.bookkoogateway.client.MemberServiceWebClient;
//import com.ssafy.bookkoo.bookkoogateway.exception.TokenExpirationException;
//import com.ssafy.bookkoo.bookkoogateway.util.TokenUtils;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.ReactiveSecurityContextHolder;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebFilter;
//import org.springframework.web.server.WebFilterChain;
//import reactor.core.publisher.Mono;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class TokenAuthenticationFilter implements WebFilter {
//
//    private static final String PASSPORT_HEADER = "Member-Passport";
//    private static final String TOKEN_PREFIX = "Bearer ";
//    private final TokenUtils tokenUtils;
//    private final MemberServiceWebClient memberServiceWebClient;
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        log.info("토큰 인증 필터 진입");
//        //헤더에서 Authorization 뽑기
//        String authorizationHeader = exchange.getRequest()
//                                             .getHeaders()
//                                             .getFirst(HttpHeaders.AUTHORIZATION);
//        //Authorization에서 Bearer를 통해 액세스 토큰 뽑기
//        String accessToken = getAccessToken(authorizationHeader);
//
//        String path = exchange.getRequest()
//                              .getURI()
//                              .getPath();
//
//        if (path.startsWith("/auth") || path.startsWith("/members/register")
//            || path.startsWith("/api-docs") || path.startsWith("/swagger-ui")
//            || path.startsWith("/docs") || path.startsWith("/webjars")
//        ) {
//            return chain.filter(exchange);
//        }
//
//        if (accessToken == null || !tokenUtils.validToken(accessToken)) {
//            log.info("토큰이 만료되었습니다.");
//            throw new TokenExpirationException();
//        }
//
//        Authentication authentication = tokenUtils.getAuthentication(accessToken);
//        String subject = tokenUtils.getSubject(accessToken);
//        SecurityContextHolder.getContext()
//                             .setAuthentication(authentication);
//
//        return memberServiceWebClient.getMemberId(subject)
//                                     .flatMap(id -> {
//                                         //Passpord 정보를 담은 헤더를 세팅
//                                         ServerHttpRequest mutatedRequest = exchange.getRequest()
//                                                                                    .mutate()
//                                                                                    .header(
//                                                                                        PASSPORT_HEADER,
//                                                                                        String.valueOf(
//                                                                                            id))
//                                                                                    .build();
//                                         //헤더 정보를 담은 Request를 추가한 exchange를 다음 필터로 전달
//                                         return chain.filter(exchange.mutate()
//                                                                     .request(mutatedRequest)
//                                                                     .build())
//                                                     .contextWrite(
//                                                         ReactiveSecurityContextHolder.withAuthentication(
//                                                             authentication));
//                                     })
//                                     .doOnError(error -> {
//                                         log.error("토큰 필터 에러 {}", error.getMessage());
//                                     });
//    }
//
//    private String getAccessToken(String authorizationHeader) {
//        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
//            return authorizationHeader.substring(TOKEN_PREFIX.length());
//        }
//        return null;
//    }
//}

package com.ssafy.bookkoo.bookkoogateway.config.filter;

import com.ssafy.bookkoo.bookkoogateway.client.MemberServiceWebClient;
import com.ssafy.bookkoo.bookkoogateway.util.TokenUtils;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class TokenAuthenticationFilter extends AuthenticationWebFilter {

    private static final String PASSPORT_HEADER = "Member-Passport";
    private static final String TOKEN_PREFIX = "Bearer ";
    private final TokenUtils tokenUtils;
    private final MemberServiceWebClient memberServiceWebClient;
    private final List<String> excludedPaths = List.of(
        "/auth-service/**", "/book-service/**",
        "/common-service/**", "/curation-service/**",
        "/library-service/**", "/member-service/**", "/api-docs/**",
        "/swagger-ui.html",
        "/swagger-ui/**",
        "/docs/**", "/webjars/**", "/v3/**"
    );

    public TokenAuthenticationFilter(
        TokenUtils tokenUtils,
        MemberServiceWebClient memberServiceWebClient
    ) {
        super(new TokenAuthenticationManager(tokenUtils));
        this.tokenUtils = tokenUtils;
        this.memberServiceWebClient = memberServiceWebClient;
        this.setServerAuthenticationConverter(new ServerAuthenticationConverter() {
            @Override
            public Mono<Authentication> convert(ServerWebExchange exchange) {
                String authHeader = exchange.getRequest()
                                            .getHeaders()
                                            .getFirst(HttpHeaders.AUTHORIZATION);
                String token =
                    (authHeader != null && authHeader.startsWith("Bearer ")) ? authHeader.substring(
                        7) : null;
                return Mono.justOrEmpty(token)
                           .filter(tokenUtils::validToken)
                           .map(tokenUtils::getAuthentication);
            }
        });
        this.setSecurityContextRepository(new WebSessionServerSecurityContextRepository());
    }
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        log.info("토큰 인증 필터 진입");
//
//        String path = exchange.getRequest()
//                              .getURI()
//                              .getPath();
//        if (excludedPaths.stream()
//                         .anyMatch(path::startsWith)) {
//            return chain.filter(exchange);
//        }
//
//        String authorizationHeader = exchange.getRequest()
//                                             .getHeaders()
//                                             .getFirst(HttpHeaders.AUTHORIZATION);
//        String accessToken = getAccessToken(authorizationHeader);
//
//        if (accessToken == null || !tokenUtils.validToken(accessToken)) {
//            log.info("토큰이 만료되었습니다.");
//            return Mono.error(new TokenExpirationException());
//        }
//
//        Authentication authentication = tokenUtils.getAuthentication(accessToken);
//        String subject = tokenUtils.getSubject(accessToken);
//        SecurityContextHolder.getContext()
//                             .setAuthentication(authentication);
//
//        return memberServiceWebClient.getMemberId(subject)
//                                     .flatMap(id -> {
//                                         ServerHttpRequest mutatedRequest = exchange.getRequest()
//                                                                                    .mutate()
//                                                                                    .header(
//                                                                                        PASSPORT_HEADER,
//                                                                                        String.valueOf(
//                                                                                            id))
//                                                                                    .build();
//                                         return chain.filter(exchange.mutate()
//                                                                     .request(mutatedRequest)
//                                                                     .build())
//                                                     .contextWrite(
//                                                         ReactiveSecurityContextHolder.withAuthentication(
//                                                             authentication));
//                                     })
//                                     .doOnError(
//                                         error -> log.error("토큰 필터 에러 {}", error.getMessage()));
//    }


    private String getAccessToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}
