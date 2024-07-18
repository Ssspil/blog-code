package com.aoxx.security.service.impl;

import com.aoxx.security.domain.User;
import com.aoxx.security.exception.NameExistException;
import com.aoxx.security.model.JoinRequest;
import com.aoxx.security.repository.UserRepository;
import com.aoxx.security.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 회원 가입
    @Override
    public User saveUser(JoinRequest joinRequest) {

        String username = joinRequest.getName();
        Boolean isExist = userRepository.existsByName(username);
        if(isExist) throw new NameExistException(username);

        String encPassword = bCryptPasswordEncoder.encode(joinRequest.getPassword());
        joinRequest.setPassword(encPassword);
        User user = joinRequest.toUserEntity(joinRequest);

        return userRepository.save(user);
    }
}
