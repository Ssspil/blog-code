package com.aoxx.oauth.domain.user.service;

import com.aoxx.oauth.domain.oauth.dto.KakaoTokenResponse;
import com.aoxx.oauth.domain.oauth.service.KakaoService;
import com.aoxx.oauth.domain.user.dto.LoginDto;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final KakaoService kakaoService;

    public LoginDto login(String code) {
        // 1. 카카오로부터 토큰 응답 받기
        KakaoTokenResponse tokenResponse = kakaoService.getAuthToken(code);

        // 2. 사용자 정보 조회
        String userResponse = kakaoService.getUserInfo(tokenResponse.getAccessToken());
        log.info("유저 정보 : {}", userResponse);

        // 3. 현재 시간
        LocalDateTime now = LocalDateTime.now();

        // 4. Dto 변환
        LoginDto loginDto = new LoginDto(
                1L,
                "zz",
                tokenResponse.getAccessToken(),
                now.plusSeconds(tokenResponse.getAccessTokenExpiresIn()),
                tokenResponse.getRefreshToken(),
                now.plusSeconds(tokenResponse.getRefreshTokenExpiresIn())
        );

        return loginDto;
    }
}
