package com.ssafy.bookkoo.authservice.config;

import com.ssafy.bookkoo.authservice.config.filter.TokenAuthenticationFilter;
import com.ssafy.bookkoo.authservice.util.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final UserDetailsService userDetailsService;

    /**
     * 패스워드 암호화를 위한 인코더 빈 등록
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 특정 경로 시큐리티 필터 사용 X Swagger, Register, 시큐리티 자체 로그인 폼 사용 X 로그아웃 사용 X csrf 필터 사용 X
     *
     * @param httpSecurity
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(auth -> auth.requestMatchers(
                                                                  //TODO: 개발 끝나면 필터 적용 다시 하기
                                                                  new AntPathRequestMatcher("/auth/**"),
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
                           //필터 추가
                           .addFilterBefore(new TokenAuthenticationFilter(tokenProvider),
                               UsernamePasswordAuthenticationFilter.class)
                           .formLogin(AbstractHttpConfigurer::disable)
                           .logout(AbstractHttpConfigurer::disable)
                           .csrf(AbstractHttpConfigurer::disable)
                           .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
        BCryptPasswordEncoder bCryptPasswordEncoder) {

        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
