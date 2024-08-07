package com.aoxx.security.api;

import com.aoxx.security.domain.User;
import com.aoxx.security.jwt.JwtHelper;
import com.aoxx.security.model.dto.user.JoinRequest;
import com.aoxx.security.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final JwtHelper jwtHelper;

    // 회원 가입
    @PostMapping("/user/save")
    public ResponseEntity<User> saveUser(@RequestBody JoinRequest joinRequest) {
        User newUser = userService.saveUser(joinRequest);
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/user/" + newUser.getId()).toUriString());
        return ResponseEntity.created(uri).body(newUser);
    }

    @GetMapping("/admin")
    public ResponseEntity<String> adminPage(){
        return ResponseEntity.ok().body("Admin Page");
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<String> reissue(HttpServletRequest request, HttpServletResponse response,
                                          @CookieValue(name = "refreshToken", required = false) String refreshToken) {

        // 간단 유효성 검사 (1)
        if (refreshToken == null) {
            return ResponseEntity.badRequest().body("refreshToken null");
        }

        // 간단 유효성 검사 (2)
        if (!jwtHelper.validation(refreshToken)) {
            return ResponseEntity.badRequest().body("refreshToken expired");
        }

        try {
            String accessToken = userService.reissue(refreshToken);
            Cookie accessTokenCookie = createCookie(accessToken);

            // 쿠키 추가
            response.addCookie(accessTokenCookie);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }


    }

    private Cookie createCookie(String accessToken) {
        Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
        long expiration = jwtHelper.extractExpiredAt(accessToken);
        int maxAge = (int) ((expiration - new Date(System.currentTimeMillis()).getTime()) / 1000);
        accessTokenCookie.setMaxAge(maxAge);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(false);

        return accessTokenCookie;
    }
}
