package com.aoxx.security.security;


import com.aoxx.security.exception.LoginAuthenticationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class LoginAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // [로그인 실행 2] 실제 인증 프로세스 구현
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails user = userDetailsService.loadUserByUsername((String) authentication.getPrincipal());

        // 비밀번호 체크
        if(bCryptPasswordEncoder.matches((String)authentication.getCredentials(), user.getPassword())){
            return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities()); // 인증 된 객체
        } else {
            throw new LoginAuthenticationException("비밀번호가 다릅니다.");
        }
    }

    // 해당 인증로직 지원하는지 검사
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}