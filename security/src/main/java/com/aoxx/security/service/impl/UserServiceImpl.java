package com.aoxx.security.service.impl;

import com.aoxx.security.domain.User;
import com.aoxx.security.exception.EmailExistException;
import com.aoxx.security.model.dto.security.CustomUserDetails;
import com.aoxx.security.model.dto.user.JoinRequest;
import com.aoxx.security.repository.UserRepository;
import com.aoxx.security.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService , UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // [로그인 실행 3] security DB 로그인 인증
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User findUser = userRepository.findByEmail(username);

        if(findUser == null){
            throw new UsernameNotFoundException("해당 유저를 찾을 수 없습니다.");
        }
        return new CustomUserDetails(findUser);
    }

    // 회원 가입
    @Override
    public User saveUser(JoinRequest joinRequest) {

        String email = joinRequest.getEmail();
        Boolean isExist = userRepository.existsByEmail(email);
        if(isExist) throw new EmailExistException(email);

        String encPassword = bCryptPasswordEncoder.encode(joinRequest.getPassword());
        joinRequest.setPassword(encPassword);
        User user = joinRequest.toUserEntity(joinRequest);

        return userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
