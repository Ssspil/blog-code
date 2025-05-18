package com.aoxx.oauth.domain.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class KakaoService {

    @Value("${kakao.auth.url}")
    private String KAKAO_URL;

    @Value("${kakao.auth.client.id}")
    private String KAKAO_CLIENT_ID;

    @Value("${kakao.redirect.url}")
    private String KAKAO_REDIRECT_URL;

    @Value("${kakao.token.url}")
    private String KAKAO_TOKEN_URL;

    private final WebClient webClient;


    /**
     * 인증 요청 코드 생성
     * @return
     */
    public String getAuthorizationCode(){
        return UriComponentsBuilder.fromHttpUrl(KAKAO_URL)
                .queryParam("client_id", KAKAO_CLIENT_ID)
                .queryParam("redirect_uri", KAKAO_REDIRECT_URL)
                .queryParam("response_type", "code")
                .build()
                .toUriString();
    }

    public String getAccessToken(String authorizeCode) {
        return webClient.post()
                .uri(KAKAO_TOKEN_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("grant_type", "authorization_code")
                        .with("client_id", KAKAO_CLIENT_ID)
                        .with("redirect_uri", KAKAO_REDIRECT_URL)
                        .with("code", authorizeCode))
                .retrieve()
                .bodyToMono(String.class)
//                .map(KakaoTokenResponse::getAccessToken)
                .block(); // 블로킹 방식 (필요 시 reactive하게 리팩토링 가능)
    }
}
