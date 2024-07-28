package com.ssafy.bookkoo.authservice.config;

import com.ssafy.bookkoo.authservice.handler.OAuth2SuccessHandler;
import com.ssafy.bookkoo.authservice.repository.OAuth2AuthorizationRequestBasedOnRepository;
import com.ssafy.bookkoo.authservice.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final OAuth2AuthorizationRequestBasedOnRepository oAuth2AuthorizationRequestBasedOnRepository;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .csrf(AbstractHttpConfigurer::disable)
            .headers(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers(
                    "/auth-service/**",
                    "/api-docs/**",
                    "/auth/login/**",
                    "/auth/token",
                    "/css/**",
                    "/images/**",
                    "/js/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/docs/**",
                    "/webjars/**",
                    "/v3/api-docs/**",
                    "/swagger-resources/**"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                .authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint
                    .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnRepository))
                .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                    .userService(customOAuth2UserService))
                .successHandler(oAuth2SuccessHandler))
            .build();
    }
}