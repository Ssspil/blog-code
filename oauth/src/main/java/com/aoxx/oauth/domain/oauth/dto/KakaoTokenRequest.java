package com.aoxx.oauth.domain.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoTokenRequest {
    private String grant_type;
    private String client_id;
    private String redirect_uri;
    private String code;

    // DTO 객체 생성
    public static KakaoTokenRequest create(String grant_type, String client_id, String redirect_uri, String code) {
        return new KakaoTokenRequest(grant_type, client_id, redirect_uri, code);
    }
}
