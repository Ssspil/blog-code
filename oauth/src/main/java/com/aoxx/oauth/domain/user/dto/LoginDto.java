package com.aoxx.oauth.domain.user.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginDto {
    // 사용자ID
    private long id;
    // 닉네임
    private String nickname;
    // 접근 토큰
    private String accessToken;
    // 접근 토큰 만료시간
    private LocalDateTime accessTokenExpiresAt;
    // 재발급 토큰
    private String refreshToken;
    // 재발급 토큰 만료시간
    private LocalDateTime refreshTokenExpiresAt;
}
