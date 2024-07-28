package com.aoxx.security.config;

import com.aoxx.security.domain.UserRole;
import com.aoxx.security.filter.JwtAuthenticationFilter;
import com.aoxx.security.jwt.JwtHelper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    // 시큐리티에게 AuthenticationConfiguration 주입 받기
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtHelper jwtHelper;
    @Value("${spring.security.cors.allowed-methods}")
    private String[] ALLOW_METHODS;
    @Value("${spring.security.cors.allowed-origins}")
    private String  ALLOW_CROSS_ORIGIN_DOMAIN;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        final String[] ALL_URL = new String[]{"/api/user/login", "/api/user/save"};
        final String[] ADMIN_URL = new String[]{"/api/admin"};
        final String[] ROLES = new String[]{UserRole.SUPER.getCode(), UserRole.MANAGER.getCode(), UserRole.ADMIN.getCode()};

        http
                .cors((cors -> cors.configurationSource(new CorsConfigurationSource() {

                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                        CorsConfiguration configuration = new CorsConfiguration();

                        configuration.setAllowedOrigins(List.of(ALLOW_CROSS_ORIGIN_DOMAIN));
                        configuration.setAllowedMethods(List.of(ALLOW_METHODS));
                        configuration.setAllowedHeaders(Collections.singletonList("*"));
                        configuration.setAllowCredentials(true);
                        configuration.setMaxAge(3600L);

                        return configuration;
                    }
                })));
        // csrf disable
        http
                .csrf((auth) -> auth.disable());

        // From 로그인 방식 disable
        http
                .formLogin((auth) -> auth.disable());

        // http basic 인증 방식 disable
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(ALL_URL).permitAll()
                        .requestMatchers(ADMIN_URL).hasAnyRole(ROLES)
                        .anyRequest().authenticated());

        // AuthenticationManager 주입 받아야해서 Bean으로 따로 등록
        http
                .addFilterAt(new JwtAuthenticationFilter(authenticationManager(authenticationConfiguration), jwtHelper), UsernamePasswordAuthenticationFilter.class);

        // 세션 설정
        http
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    // 인증 관리자
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

}
