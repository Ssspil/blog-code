package com.aoxx.security.service;

import com.aoxx.security.domain.User;
import com.aoxx.security.model.JoinRequest;

public interface UserService {
    User saveUser(JoinRequest joinRequest);
}
