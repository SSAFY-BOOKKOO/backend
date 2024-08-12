package com.ssafy.bookkoo.authservice.config;

import com.ssafy.bookkoo.authservice.handler.OAuth2SuccessHandler;
import com.ssafy.bookkoo.authservice.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.ssafy.bookkoo.authservice.service.CustomOAuth2MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final CustomOAuth2MemberService oAuth2MemberService;
    private final OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .csrf(AbstractHttpConfigurer::disable)
            .headers(AbstractHttpConfigurer::disable)
            .logout(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .anyRequest()
                .permitAll())
            .oauth2Login((oauth2) -> oauth2
                .authorizationEndpoint(
                    //인증 요청 설정 엔드포인트 (소셜 로그인 페이지 요청 URL)
                    endPoint -> endPoint.authorizationRequestRepository(
                                            oAuth2AuthorizationRequestBasedOnCookieRepository)
                                        //해당 URL로 요청 시 로그인 페이지로 이동 (.../authorization/provider(google,kakao))
                                        .baseUri("/auth/login/oauth2/authorization"))
                .redirectionEndpoint(redirection -> redirection
                    .baseUri("/auth/login/oauth2/code/*"))
                .userInfoEndpoint(
                    userInfoEndPoint -> userInfoEndPoint.userService(
                        oAuth2MemberService))
                //성공 시 핸들링
                .successHandler(oAuth2SuccessHandler)
                .failureUrl("/login?error=true"))

            .build();
    }

}