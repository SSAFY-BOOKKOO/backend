//package com.ssafy.bookkoo.bookkoogateway.config;
//
//
//import com.ssafy.bookkoo.bookkoogateway.config.filter.TokenAuthenticationFilter;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity.AuthorizeExchangeSpec;
//import org.springframework.security.config.web.server.ServerHttpSecurity.CsrfSpec;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
//import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher.MatchResult;
//
//@Configuration
//@EnableWebFluxSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    private final TokenAuthenticationFilter tokenAuthenticationFilter;
//
//    @Bean
//    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity httpSecurity) {
//        return httpSecurity.csrf(CsrfSpec::disable)
//                           //해당 uri 매칭 되는 요청은 인증 필요 X
//                           .authorizeExchange(exchanges -> exchanges
//                               .pathMatchers("/auth-service/**", "/book-service/**",
//                                   "/common-service/**", "/curation-service/**",
//                                   "/library-service/**", "/member-service/**", "/api-docs/**",
//                                   "/swagger-ui.html",
//                                   "/swagger-ui/**",
//                                   "/docs/**", "/webjars/**", "/v3/**")
//                               .permitAll() // 해당 경로는 필터링하지 않음
//                               .anyExchange()
//                               .authenticated()
//                           )
////                           .authorizeExchange(exchangeSpecCustomizer())
//                           .addFilterBefore(tokenAuthenticationFilter,
//                               SecurityWebFiltersOrder.AUTHENTICATION)
//                           .build();
//    }
//
//    @Bean
//    public ServerWebExchangeMatcher customServerWebExchangeMatcher() {
//        //ServerWebExchangeMatcher 익명 객체 빈 등록
//        return (exchange) -> {
//            ServerHttpRequest request = exchange.getRequest();
//            String path = request.getURI()
//                                 .getPath();
//            //인증 or 멤버 등록에 관련된 요청이면 매칭
//            if (path.startsWith("/auth") || path.startsWith("/members/register")
//                || path.startsWith("/api-docs") || path.startsWith("/swagger-ui")
//                || path.startsWith("/docs") || path.startsWith("/webjars")) {
//                return MatchResult.match();
//            }
//            //나머지는 매칭 X
//            return MatchResult.notMatch();
//        };
//    }
//
//    @Bean
//    public Customizer<AuthorizeExchangeSpec> exchangeSpecCustomizer() {
//        return authorizeExchangeSpec -> authorizeExchangeSpec.matchers(
//                                                                 customServerWebExchangeMatcher())
//                                                             .permitAll()
//                                                             .anyExchange()
//                                                             .authenticated();
//    }
//}

package com.ssafy.bookkoo.bookkoogateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity httpSecurity) {
        return httpSecurity.csrf(ServerHttpSecurity.CsrfSpec::disable)
                           .authorizeExchange(exchanges -> exchanges
                               .anyExchange()
                               .permitAll() // 모든 경로를 허용
                           )
                           .build();
    }
}