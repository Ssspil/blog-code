package com.aoxx.oauth.global.config;

import com.aoxx.oauth.global.security.LoginAuthenticationFilter;
import com.aoxx.oauth.global.security.LoginFailHandler;
import com.aoxx.oauth.global.security.LoginSuccessHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Value("${spring.security.cors.allowed-origins}")
    private List<String> allowedOrigins;

    @Value("${spring.security.cors.allowed-methods}")
    private List<String> allowedMethods;

    private final ObjectMapper objectMapper;

    // 시큐리티에게 AuthenticationConfiguration 주입 받기
    private final AuthenticationConfiguration authenticationConfiguration;

    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailHandler loginFailHandler;



    // 암호화를 위해
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 상속으로 새로운 클래스를 사용하기 위해 Config에 정의
    @Bean
    public LoginAuthenticationFilter loginAuthenticationFilter() throws Exception {
        LoginAuthenticationFilter loginAuthenticationFilter = new LoginAuthenticationFilter(objectMapper);
        loginAuthenticationFilter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        loginAuthenticationFilter.setFilterProcessesUrl("/api/login");  // 로그인 경로 /login -> /api/login 으로 변경
        loginAuthenticationFilter.setAuthenticationSuccessHandler(loginSuccessHandler);  // 로그인 성공했을 때 실행시킬 핸들러
        loginAuthenticationFilter.setAuthenticationFailureHandler(loginFailHandler);      // 로그인 실패했을 때 실행시킬 핸들러
        return loginAuthenticationFilter;
    }

    // 시큐리티 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        final String[] ALL_URL = new String[]{"/api/**", "/login"};

        // CORS 설정
        http.cors((cors -> cors.configurationSource(new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration configuration = new CorsConfiguration();

                configuration.setAllowedOrigins(allowedOrigins);            // Cors 도메인 (Credentials 때문에 필수)
                configuration.setAllowedMethods(allowedMethods);            // HTTP 메서드
                configuration.setAllowedHeaders(Collections.singletonList("*"));    // 헤더 값 다 허용
                configuration.setAllowCredentials(true);                    // 인증 관련 정보 (JWT, 세션 쿠키 받기 위함)
                configuration.setMaxAge(3600L);                         // 브라우저의 preflight 요청 캐싱 시간

                return configuration;
            }
        })));

        // Form 형식이 아니기 때문에 disabled 처리
        http.csrf((csrf) -> csrf.disable());

        // 인증 커스텀 (hasRole : 역할, hasAuthority : 권한) // TODO 관리자 경로 설정
        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers(ALL_URL).permitAll()       // 누구나 접근 가능
                .anyRequest().authenticated()       // 이외의 경로 모두 인증 필요
        );

        http
                .addFilterAt(loginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);  // 내가 만든 로그인 필터로 대체(교체)

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}

























