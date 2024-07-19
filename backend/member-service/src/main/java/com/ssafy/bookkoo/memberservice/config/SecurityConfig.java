package com.ssafy.bookkoo.memberservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * 패스워드 암호화를 위한 인코더 빈 등록
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 특정 경로 시큐리티 필터 사용 X
     * Swagger, Register,
     * 시큐리티 자체 로그인 폼 사용 X
     * 로그아웃 사용 X
     * csrf 필터 사용 X
     * @param httpSecurity
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(auth -> auth.requestMatchers(
                                                                  new AntPathRequestMatcher("/member/register"),
                                                                  new AntPathRequestMatcher("/member/**"),
                                                                  new AntPathRequestMatcher("/swagger-ui/**"),
                                                                  new AntPathRequestMatcher("/v3/api-docs/**"),
                                                                  new AntPathRequestMatcher("/api-docs/**"),
                                                                  new AntPathRequestMatcher("/swagger-ui.html"),
                                                                  new AntPathRequestMatcher("/swagger-ui/**"),
                                                                  new AntPathRequestMatcher("/swagger-resources/**"),
                                                                  new AntPathRequestMatcher("/webjars/**")
                                                              )
                                                              .permitAll()
                                                              .anyRequest()
                                                              .authenticated())
                           .formLogin(AbstractHttpConfigurer::disable)
                           .logout(AbstractHttpConfigurer::disable)
                           .csrf(AbstractHttpConfigurer::disable)
                           .build();
    }
}
