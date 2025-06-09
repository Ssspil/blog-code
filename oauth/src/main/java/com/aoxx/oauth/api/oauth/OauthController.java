package com.aoxx.oauth.api.oauth;

import com.aoxx.oauth.domain.oauth.dto.KakaoTokenResponse;
import com.aoxx.oauth.domain.oauth.service.KakaoService;
import com.aoxx.oauth.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
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
     * 카카오 로그인 하기전 인증 URL 요청
     * @return
     */
    @GetMapping("/kakao/authorize")
    public String kakaoLoginRequest() {
        log.info("KaKao Login =====>> 인증 URL 가져오기");
        return kakaoService.getAuthUrl();
    }

    /**
     * 카카오 로그인 콜백
     * @param code
     * @return
     */
    @GetMapping("/kakao/redirect")
    public KakaoTokenResponse kakaoLoginRedirect(@RequestParam String code, HttpServletResponse response) throws IOException {
        log.info("KaKao AuthCode callback =====>> {}", code);

        KakaoTokenResponse res = userService.login(code);


//        response.sendRedirect(redirectUrl);
        return res;
    }
}



