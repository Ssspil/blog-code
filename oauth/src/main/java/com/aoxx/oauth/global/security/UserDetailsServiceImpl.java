package com.aoxx.oauth.global.security;

import com.aoxx.oauth.domain.user.entity.User;
import com.aoxx.oauth.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


/**********************************
 * 스프링 시큐리티 Provider에서 사용자 인증하기 전 조회 서비스 구현
 *********************************/
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     *  사용자 정보 조회
     * @param phoneNumber 사용자 폰번호
     * @return
     * @throws UsernameNotFoundException    사용자 못찾았을 때
     */
    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        log.debug("Security Login =====>> 사용자 정보 조회");

        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다."));

        return new CustomUserDetails(user);
    }
}