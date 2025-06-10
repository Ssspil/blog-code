package com.aoxx.oauth.domain.oauth.service;

import com.aoxx.oauth.domain.oauth.dto.KakaoTokenResponse;
import com.aoxx.oauth.domain.oauth.dto.KakaoUserInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
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

    private final RestClient restClient;

    /**
     * 인증 요청 코드 생성
     * @return
     */
    public String getAuthUrl(){
        return UriComponentsBuilder.fromHttpUrl(KAKAO_URL)
                .queryParam("client_id", KAKAO_CLIENT_ID)
                .queryParam("redirect_uri", KAKAO_REDIRECT_URL)
                .queryParam("response_type", "code")
                .build()
                .toUriString();
    }


    /**
     * 카카오 토큰 받기
     * @param code
     * @return
     * @throws Exception
     */
    public KakaoTokenResponse getTokenFromKakao(String code) {
        String params = String.format("grant_type=authorization_code&client_id=%s&client_secret=%s&code=%s",
                KAKAO_CLIENT_ID, KAKAO_REDIRECT_URL, code);
        KakaoTokenResponse token = restClient.post()
                .uri(KAKAO_TOKEN_URL)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .body(params)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(KakaoTokenResponse.class);

        return token;
    }

    /**
     * 사용자 정보 조회
     * @param kakaoToken
     * @return
     */
    public KakaoUserInfoResponse getUserProfile(String kakaoToken) {
        KakaoUserInfoResponse userInfo = restClient.get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .headers((header) -> {
                    header.setBearerAuth(kakaoToken);
                })
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(KakaoUserInfoResponse.class);

        return userInfo;

    }


}
