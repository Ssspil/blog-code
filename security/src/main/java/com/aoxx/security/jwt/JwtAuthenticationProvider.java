package com.aoxx.security.jwt;


import com.aoxx.security.jwt.dto.JwtAuthenticationToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtHelper jwtHelper;
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // [실행2] 실제 인증 프로세스 구현
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken unAuthentication = (JwtAuthenticationToken) authentication;

        String encPaswword = bCryptPasswordEncoder.encode((String)authentication.getCredentials());
        UserDetails user = userDetailsService.loadUserByUsername((String)unAuthentication.getPrincipal());

        if(!bCryptPasswordEncoder.matches(encPaswword, user.getPassword())){
            return new JwtAuthenticationToken(user.getUsername(), "", user.getAuthorities()); // 인증 된 객체
        } else {
            throw new AuthenticationException("비밀번호가 다릅니다.") {};    // 익명 클래스로 예외 던지기
        }
    }

    // 해당 인증로직 지원하는지 검사
    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
