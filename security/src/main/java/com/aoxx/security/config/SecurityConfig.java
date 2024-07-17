package com.aoxx.security.config;

import com.aoxx.security.domain.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        final String[] ALL_URL = new String[]{"/api/user/login", "/api/user/save"};
        final String[] ADMIN_URL = new String[]{"/api/admin"};
        final String[] ROLES = new String[]{UserRole.SUPER.getCode(), UserRole.MANAGER.getCode(), UserRole.ADMIN.getCode()};


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

        // 세션 설정
        http
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
