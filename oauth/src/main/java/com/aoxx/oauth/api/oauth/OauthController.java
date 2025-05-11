package com.aoxx.oauth.api.oauth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class OauthController {

    @GetMapping("/kakao/login")
    public String kakaoLogin() {
        log.info("들어왔나?");
        String authorizationCode = "https://kauth.kakao.com/oauth/authorize?";
        authorizationCode += "client_id=fc419b64f9cb8449d8c68a2bf939e193&";
        authorizationCode += "redirect_uri=http://192.168.35.39:8080/api/auth&";
        authorizationCode += "response_type=code";

        return authorizationCode;
    }


    @GetMapping
    public String kakaoLogin2(@RequestParam String code) {
        log.info("코드 : {}", code);


        return "test";
    }
}



