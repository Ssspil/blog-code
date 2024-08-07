package com.aoxx.security.service;

import com.aoxx.security.domain.User;
import com.aoxx.security.model.dto.user.JoinRequest;

public interface UserService {
    User saveUser(JoinRequest joinRequest);

    User findUserByEmail(String email);

    String reissue(String refreshToken);
}
