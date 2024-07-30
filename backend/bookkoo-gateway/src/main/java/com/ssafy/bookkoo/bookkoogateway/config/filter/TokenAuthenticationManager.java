//package com.ssafy.bookkoo.bookkoogateway.config.filter;
//
//import com.ssafy.bookkoo.bookkoogateway.util.TokenUtils;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.ReactiveAuthenticationManager;
//import org.springframework.security.core.Authentication;
//import reactor.core.publisher.Mono;
//
//@RequiredArgsConstructor
//public class TokenAuthenticationManager implements ReactiveAuthenticationManager {
//
//    private final TokenUtils tokenUtils;
//
//    @Override
//    public Mono<Authentication> authenticate(Authentication authentication) {
//        String token = (String) authentication.getCredentials();
//        if (tokenUtils.validToken(token)) {
//            return Mono.just(tokenUtils.getAuthentication(token));
//        } else {
//            return Mono.empty();
//        }
//    }
//}
