package com.aoxx.security.model;

import com.aoxx.security.domain.User;
import com.aoxx.security.domain.UserRole;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JoinRequest {

    private String name;
    private String password;

    public User toUserEntity(JoinRequest joinRequest){
        return User.builder()
                .name(joinRequest.getName())
                .password(joinRequest.getPassword())
                .role(UserRole.ADMIN)
                .build();
    }
}
