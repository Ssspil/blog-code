package com.aoxx.oauth.global.security;

import com.aoxx.oauth.domain.user.dto.LoginResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


/**********************************
 * 스프링 시큐리티 로그인, 인증 중 실패 했을 때 실행할 로직
 *********************************/
@Slf4j
@Component
@RequiredArgsConstructor
public class LoginFailHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    /**
     * 스프링 시큐리티 로그인 중 실패 했을 때 처리
     * @param request
     * @param response
     * @param exception
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        // 응답 상태값 작성
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // 응답 메시지 작성
        LoginResponse loginResponse = new LoginResponse(HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage());

        // 응답
        response.getWriter().write(objectMapper.writeValueAsString(loginResponse));
    }
}
