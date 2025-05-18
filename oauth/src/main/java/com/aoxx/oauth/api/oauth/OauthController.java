package com.aoxx.oauth.api.oauth;

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
    public String kakaoLogin(@RequestParam String code) {
        log.info("코드 : {}", code);


        return "test";
    }
}



