package com.aoxx.security.jwt.dto;

import com.aoxx.security.domain.UserRole;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

    // 인증 완료 전에 객체 생성
    public JwtAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    // 인증 완후 후의 객체 생성
    public JwtAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

}
