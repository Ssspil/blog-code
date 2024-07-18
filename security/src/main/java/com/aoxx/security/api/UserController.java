package com.aoxx.security.api;

import com.aoxx.security.domain.User;
import com.aoxx.security.model.JoinRequest;
import com.aoxx.security.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    // 회원 가입
    @PostMapping("/user/save")
    public ResponseEntity<User> saveUser(@RequestBody JoinRequest joinRequest) {
        User newUser = userService.saveUser(joinRequest);
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/user/" + newUser.getId()).toUriString());
        return ResponseEntity.created(uri).body(newUser);
    }

}
