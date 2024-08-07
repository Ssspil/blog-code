package com.aoxx.security.security;

import com.aoxx.security.domain.UserRole;
import com.aoxx.security.jwt.JwtHelper;
import com.aoxx.security.model.dto.user.LoginResponse;
import com.aoxx.security.service.impl.RedisService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtHelper jwtHelper;
    private final RedisService redisService;

    // [로그인 실행 5] 인증 성공하여 로그인 성공하면 실행하는 핸들러
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse(UserRole.ADMIN.getCode());

        String accessToken = jwtHelper.generateAccessToken(email, role);
        String refreshToken = jwtHelper.generateRefreshToken(email);

        // Redis에 refreshToken 유효시간만큼 캐시
        redisService.save(email, refreshToken, Duration.ofMillis(jwtHelper.extractExpiredAt(refreshToken)));

        // 방법 1) 쿠키에 넣어서 전달
        Cookie accessTokenCookie = createCookie(accessToken, "accessToken");
        Cookie refreshTokenCookie = createCookie(refreshToken, "refreshToken");

        // 방법 2) HTTP header에 넣어서 전달
        //response.addHeader("Authorizetion", "Bearer " + accessToken);

        // 응답 메시지 작성
        String jsonResponse = new ObjectMapper().writeValueAsString(new LoginResponse(HttpServletResponse.SC_OK, "성공적으로 로그인이 되었습니다."));
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(jsonResponse);

        // 쿠키
        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
    }

    // 쿠키 내려주기
    private Cookie createCookie(String accessToken, String cookieName) {
        Cookie accessTokenCookie = new Cookie(cookieName, accessToken);
        long expiration = jwtHelper.extractExpiredAt(accessToken);
        int maxAge = (int) ((expiration - new Date(System.currentTimeMillis()).getTime()) / 1000);
        accessTokenCookie.setMaxAge(maxAge);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(false);

        return accessTokenCookie;
    }
}
