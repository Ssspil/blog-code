package com.aoxx.security.service;

import com.aoxx.security.domain.User;
import com.aoxx.security.model.dto.User.JoinRequest;

public interface UserService {
    User saveUser(JoinRequest joinRequest);
}
