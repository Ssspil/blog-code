package com.aoxx.oauth.global.security;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


/**********************************
 * 스프링 시큐리티 사용자 인증 과정 요구사항 로직을 녹여내는 곳
 *********************************/
@Slf4j
@Component
@RequiredArgsConstructor
public class LoginAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 사용자 인증 (만약 여러 요구사항이 있으면 녹여낼 것)
     * ex) IP 기반 인증, OTP 인증, 계정 잠금, 계정 만료 등
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.debug("Security Login =====>> 사용자 인증 검증 시작");
        // 사용자 정보 조회
        UserDetails user = userDetailsService.loadUserByUsername(String.valueOf(authentication.getPrincipal()));

        // 비밀번호 일치하는지 체크
        boolean isPasswordTrue = bCryptPasswordEncoder.matches(String.valueOf(authentication.getCredentials()), String.valueOf(user.getPassword()));
        boolean isUsed = true;  // TODO 사용 중인 유저인가 체크

        if(isPasswordTrue && isUsed) {
            return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities()); // 인증 된 객체

        } else {
            log.error("Security Login =====>> 비밀번호 불일치");
            throw new AuthenticationServiceException("비밀번호가 다릅니다.");    // TODO 커스텀 예외로 변경
        }
    }

    /**
     * 해당 인증 객체가 지원 가능한지 체크
     * @param authentication 인증 객체
     * @return 지원 여부
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}