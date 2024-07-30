package com.aoxx.security.security;

import com.aoxx.security.model.dto.user.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;


@Slf4j
public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    // [로그인 실행 1] API 데이터 (email, password) 로그인을 시도한다.
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String contentType = request.getContentType();
        String username = "";
        String password = "";

        // JSON으로 로그인 할 때
        if(contentType.equals(MediaType.APPLICATION_JSON_VALUE)){
            try {
                LoginRequest loginReqeust =  new ObjectMapper().readValue(request.getReader(), LoginRequest.class);
                username = loginReqeust.getEmail();
                password = loginReqeust.getPassword();
            } catch (IOException e) {
                throw new AuthenticationServiceException("잘못된 key, name으로 요청했습니다.", e);
            }
        } else if(contentType.equals(MediaType.APPLICATION_FORM_URLENCODED_VALUE)){
            username = request.getParameter("email"); // username이 아닌 email로 받기 때문에 파라미터 꺼내기
            password = this.obtainPassword(request);
        }

        // 아직 인증되기전의 인증 객체 생성
        UsernamePasswordAuthenticationToken unauthenticated = new UsernamePasswordAuthenticationToken(username , password);

        // token에 인증되지 않은 정보 검증 위해 AuthenticationManager로 전달
        return super.getAuthenticationManager().authenticate(unauthenticated);

    }

    // [로그인 실행 4] security 인증 성공 시 실행하는 메서드
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("Security Login >> 인증 성공");

        // 로그인 성공 핸들러 호출
        AuthenticationSuccessHandler handler = this.getSuccessHandler();
        handler.onAuthenticationSuccess(request, response, authResult);
    }

    // [로그인 실행 4] security 인증 실패 시 실행하는 메서드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("Security Login >> 인증 실패");

        // 로그인 실패 핸들러 호출
        AuthenticationFailureHandler handler = this.getFailureHandler();
        handler.onAuthenticationFailure(request, response, failed);
    }


}
