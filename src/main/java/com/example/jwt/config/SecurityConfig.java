package com.example.jwt.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthFilter authFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 로그인 관련 접근은 인증이 없어도 허가
                .authorizeHttpRequests(
                        auth 
                                -> auth.requestMatchers("/login/**").permitAll().anyRequest().authenticated()
                )
                
                // JWT로 사용할꺼라 Session 생성안함
                .sessionManagement(
                        session
                                -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // JWT로 인증하기에 ID/PW Filter 이전에 authFilter로 먼저 인증 처리
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                
                .csrf(csrf -> csrf.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                
                //로그인 페이지 미사용
                .formLogin(formLogin -> formLogin.disable());

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
