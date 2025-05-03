package com.aoxx.oauth.global.security;

import com.aoxx.oauth.domain.user.dto.LoginSuccessResponse;
import com.aoxx.oauth.global.jwt.JwtHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**********************************
 * 스프링 시큐리티 로그인, 인증 성공 했을 때 성공할 로직
 *********************************/
@Slf4j
@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtHelper jwtHelper;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        // 유저ID
        String email = user.getUsername();

        // 토큰 생성
        String accessToken = jwtHelper.generateAccessToken(email, user.getRole());
        String refreshToken = jwtHelper.generateRefreshToken(email);

        // 만료 시간 추출
        long accessTokenExpiresAt = jwtHelper.extractExpiredAt(accessToken);
        long refreshTokenExpiresAt = jwtHelper.extractExpiredAt(refreshToken);

        // 응답 상태값 작성
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.setStatus(HttpServletResponse.SC_OK);

        // 응답 객체 생성
        LoginSuccessResponse successResponse = LoginSuccessResponse.create(accessToken, refreshToken, accessTokenExpiresAt, refreshTokenExpiresAt);

        // 응답
        response.getWriter().write(objectMapper.writeValueAsString(successResponse));
    }
}
