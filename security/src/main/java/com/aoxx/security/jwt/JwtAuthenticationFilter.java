package com.aoxx.security.jwt;

import com.aoxx.security.domain.User;
import com.aoxx.security.domain.UserRole;
import com.aoxx.security.model.dto.security.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtHelper jwtHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = "";
        Optional<Cookie> optionalCookie = Optional.ofNullable(request.getCookies())
                .map(cookies -> Arrays.stream(cookies)
                        .filter(cookie -> cookie.getName().equals("accessToken"))
                        .findFirst())
                .orElse(Optional.empty());
        if (optionalCookie.isPresent()) {
            accessToken = optionalCookie.get().getValue();
        }

        // 토큰이 없으면 넘겨~
        if(accessToken.isEmpty()){
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰 검증
        if(!jwtHelper.validation(accessToken)) {
            
            // Front 와 협의하여 어떻게 내려줄지 해야한다.
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("유효하지 않은 토큰입니다.");

            return;
        }
        // 토큰에서 정보 획득
        String email = jwtHelper.extractSubject(accessToken);
        UserRole role = UserRole.valueOf(jwtHelper.extractRole(accessToken));


        User user = new User(email, "password", role);

        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        JwtAuthenticationToken authToken = new JwtAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        SecurityContextHolder.clearContext();
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);

    }
}
