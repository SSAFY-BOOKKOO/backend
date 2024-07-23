package com.ssafy.bookkoo.bookkoogateway.config.filter;

import com.ssafy.bookkoo.bookkoogateway.client.MemberServiceWebClient;
import com.ssafy.bookkoo.bookkoogateway.util.TokenUtils;
import lombok.RequiredArgsConstructor;
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

@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter implements WebFilter {

    private static final String PASSPORT_HEADER = "Member-Passport";
    private static final String TOKEN_PREFIX = "Bearer ";
    private final TokenUtils tokenUtils;
    private final MemberServiceWebClient memberServiceWebClient;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        //헤더에서 Authorization 뽑기
        String authorizationHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        //Authorization에서 Bearer를 통해 액세스 토큰 뽑기
        String accessToken = getAccessToken(authorizationHeader);

        if (accessToken != null && tokenUtils.validToken(accessToken)) {
            Authentication authentication = tokenUtils.getAuthentication(accessToken);
            String subject = tokenUtils.getSubject(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            System.out.println("Request received with valid token, subject: " + subject);

            return memberServiceWebClient.getMemberId(subject)
                                      .flatMap(id -> {
                                          ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                                                                                     .header(PASSPORT_HEADER, String.valueOf(id))
                                                                                     .build();
                                          ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();

                                          System.out.println("Received member ID: " + id);

                                          return chain.filter(mutatedExchange)
                                                      .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
                                      })
                                      .doOnError(error -> {
                                          System.err.println("Error occurred: " + error.getMessage());
                                      });
        } else {
            System.out.println("토큰 엄서");
            return chain.filter(exchange);
        }
    }

    private String getAccessToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}
