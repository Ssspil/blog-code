package com.aoxx.security.service.impl;

import com.aoxx.security.domain.User;
import com.aoxx.security.exception.EmailExistException;
import com.aoxx.security.jwt.JwtHelper;
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

import java.util.Optional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService , UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtHelper jwtHelper;
    private final RedisService redisService;

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

    // 사용자 찾기
    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // 토큰 재발급
    @Override
    public String reissue(String refreshToken) {
        // 유저 인증
        String email = jwtHelper.extractSubject(refreshToken);

        // refreshToken 레디스에서 찾기
        Optional<String> optionalToken = redisService.find(email);
        if (optionalToken.isPresent()) {
            // refreshToken 유효성 검증
            if (!optionalToken.get().equals(refreshToken)) {
                throw new RuntimeException("유효하지 않은 refreshToken 입니다.");
            }

            User user = findUserByEmail(email);
            return jwtHelper.generateAccessToken(email, user.getRole().getCode());
        } else {
            throw new RuntimeException("존재하지 않는 토큰 입니다.");
        }
    }

}
