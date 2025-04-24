package com.aoxx.oauth.global.security;

import com.aoxx.oauth.domain.user.dto.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;

    @Override
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    /********************
     * 로그인 시도
     ***************/
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // JSON 타입으로 받아야함
        if (request.getContentType() == null || !request.getContentType().contains(MediaType.APPLICATION_JSON_VALUE)) {
            throw new AuthenticationServiceException("잘못된 Content-Type으로 요청하였습니다.");    // TODO 커스텀 예외로 변경
        }

        try {
            log.debug("=============== 로그인 시도 ===============");
            // 데이터 바인딩 (getInputStream은 1번 밖에 못 읽음)
            LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);

            // 아직 인증되기전의 인증 객체 생성
            UsernamePasswordAuthenticationToken unauthenticated = new UsernamePasswordAuthenticationToken(loginRequest.getPhoneNumber() , loginRequest.getPassword());

            // token에 인증되지 않은 정보 검증 위해 AuthenticationManager로 전달
            return super.getAuthenticationManager().authenticate(unauthenticated);

        } catch (IOException e) {
            throw new AuthenticationServiceException("잘못된 Key 이름으로 요청하였습니다.", e);
        }
    }

    /********************
     * security 인증 성공 시 실행하는 메서드
     ***************/
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
    }

    /********************
     * security 인증 실패 시 실행하는 메서드
     ***************/
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
