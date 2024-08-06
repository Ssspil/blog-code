package com.aoxx.security.api;

import com.aoxx.security.domain.User;
import com.aoxx.security.domain.UserRole;
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
        return new ResponseEntity<String>("Admin Page", HttpStatus.OK);
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<String> reissue(HttpServletRequest request, HttpServletResponse response,
                                          @CookieValue(name = "refreshToken", required = false) String refreshToken) {

        // 간단 유효성 검사 (1)
        if(refreshToken == null){
            return new ResponseEntity<>("refreshToken null", HttpStatus.BAD_REQUEST);
        }

        // 간단 유효성 검사 (2)
        if(!jwtHelper.validation(refreshToken)){
            return new ResponseEntity<>("refreshToken expired", HttpStatus.BAD_REQUEST);
        }

        // 유저 인증
        String email = jwtHelper.extractSubject(refreshToken);
        User user = userService.findUserByEmail(email);

        // accesToken 새롭게 생성
        String accessToken = jwtHelper.generateAccessToken(email, user.getRole().getCode());
        Cookie accessTokenCookie = createCookie(accessToken, "accessToken");

        // 쿠키 추가
        response.addCookie(accessTokenCookie);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Cookie createCookie(String accessToken, String cookieName) {
        Cookie accessTokenCookie = new Cookie(cookieName, accessToken);
        long expiration = jwtHelper.extractExpiredAt(accessToken);
        int maxAge = (int) ((expiration - new Date(System.currentTimeMillis()).getTime()) / 1000);
        accessTokenCookie.setMaxAge(maxAge);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(false);

        return accessTokenCookie;
    }
}
