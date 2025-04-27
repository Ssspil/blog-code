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
@Transactional
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    // 사용자 정보 조회
    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        log.debug("Security Login =====>> 사용자 정보 조회");

        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);    // 삭제X, 미사용X, 약관 동의O 한 유저로 조건 조회

        if(optionalUser.isEmpty()){
            throw new UsernameNotFoundException("해당 유저를 찾을 수 없습니다.");   // TODO 커스텀 예외로 변경
        }
        return new CustomUserDetails(optionalUser.get());
    }
}