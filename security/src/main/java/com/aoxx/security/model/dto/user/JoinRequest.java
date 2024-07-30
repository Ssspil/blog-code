package com.aoxx.security.model.dto.user;

import com.aoxx.security.domain.User;
import com.aoxx.security.domain.UserRole;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JoinRequest {

    private String email;
    private String password;

    public User toUserEntity(JoinRequest joinRequest){
        return User.builder()
                .email(joinRequest.getEmail())
                .password(joinRequest.getPassword())
                .role(UserRole.ADMIN)
                .build();
    }
}
