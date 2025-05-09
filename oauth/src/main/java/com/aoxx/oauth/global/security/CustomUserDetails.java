package com.aoxx.oauth.global.security;

import com.aoxx.oauth.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;


/**********************************
 * 스프링 시큐리티 사용자 객체
 *********************************/
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user;

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add((GrantedAuthority) () -> user.getAuth().getCode());

        return authorities;
    }

    public String getRole() {
        return user.getAuth().getCode();
    }

    // 계정이 만료되지 않는지 (true 여야 통과)
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    // 계정이 잠기지 않았는지 (true 여야 통과)
    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    // 비밀번호가 만료되지 않았는지 (true 여야 통과)
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    // 계정이 활성화 되었는지 (true 여야 통과)
    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}