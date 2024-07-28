package com.aoxx.security.filter;

import com.aoxx.security.domain.UserRole;
import com.aoxx.security.jwt.JwtHelper;
import com.aoxx.security.jwt.dto.JwtAuthenticationToken;
import com.aoxx.security.model.dto.User.LoginRequest;
import com.aoxx.security.model.dto.User.LoginResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jdk.jfr.ContentType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.manager.Constants;
import org.springframework.boot.web.servlet.server.Encoding;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;


@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtHelper jwtHelper;

    // [실행1] 인증 필터 실행 : accessToken 값 가져와서 인증 해달라고 인증 책임 위임
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException{
        String contentType = request.getContentType();
        String username = "";
        String password = "";

        // JSON으로 로그인 할 때
        if(contentType.equals(MediaType.APPLICATION_JSON_VALUE)){
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                LoginRequest loginReqeust =  objectMapper.readValue(request.getInputStream(), LoginRequest.class);
                username = loginReqeust.getEmail();
                password = loginReqeust.getPassword();
            } catch (IOException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);     // 잘못 요청 400
                return null;
            }
        } else if(contentType.equals(MediaType.APPLICATION_FORM_URLENCODED_VALUE)){
            username = request.getParameter("email"); // username이 아닌 email로 받기 때문에 파라미터 꺼내기
            password = this.obtainPassword(request);
        }

        // 아직 인증되기전의 인증 객체 생성
        JwtAuthenticationToken unauthenticated = new JwtAuthenticationToken(username , password);

        // token에 인증되지 않은 정보 검증 위해 AuthenticationManager로 전달
        return authenticationManager.authenticate(unauthenticated);

    }

    // [실행3-1] security 로그인 성공시 실행하는 메서드
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        JwtAuthenticationToken authentication = (JwtAuthenticationToken) authResult;
        String email = (String)authentication.getPrincipal();

        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        String role = authorities.stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse(UserRole.ADMIN.getCode());

        // 방법 1) HTTP header에 넣어서 전달
        // response.addHeader("Authorizetion", "Bearer " + authToken);

        // 방법 2) 쿠키에 넣어서 전달
        String accessToken = jwtHelper.generateAccessToken(email, role);
        cookieResponse(response, accessToken);


        // 응답 메시지 작성
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(new LoginResponse(200, "성공적으로 로그인이 되었습니다."));
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());

        response.getWriter().write(jsonResponse);
    }

    // [실행3-2] security 로그인 실패시 실행하는 메서드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    // 쿠키 내려주기
    private void cookieResponse(HttpServletResponse response, String accessToken) {
        Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
        long expiration = jwtHelper.extractExpiredAt(accessToken);
        int maxAge = (int) ((expiration - new Date(System.currentTimeMillis()).getTime()) / 1000);
        accessTokenCookie.setMaxAge(maxAge);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setSecure(false);
        response.addCookie(accessTokenCookie);
    }

}
