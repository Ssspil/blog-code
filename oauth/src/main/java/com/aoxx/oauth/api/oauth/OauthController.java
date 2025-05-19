package com.aoxx.oauth.api.oauth;

import com.aoxx.oauth.domain.oauth.service.KakaoService;
import com.aoxx.oauth.domain.user.dto.LoginDto;
import com.aoxx.oauth.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
public class OauthController {

    private final KakaoService kakaoService;
    private final UserService userService;

    /**
     * 카카오 로그인 하기전 인증 코드 요청
     * @return
     */
    @GetMapping("/kakao/login")
    public String kakaoLoginRequest() {
        log.info("KaKao Login =====>> 로그인 시도");
        return kakaoService.getAuthorizationCode();
    }

    /**
     * 카카오 로그인
     * @param code
     * @return
     */
    @GetMapping("/kakao")
    public LoginDto kakaoLogin(@RequestParam String code, HttpServletResponse response) {
        log.info("KaKao AuthCode callback =====>> {}", code);

        return userService.login(code);
    }
}



