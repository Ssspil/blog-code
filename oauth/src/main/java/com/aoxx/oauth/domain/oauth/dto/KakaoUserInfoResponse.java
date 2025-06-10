package com.aoxx.oauth.domain.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoUserInfoResponse {

    private Long id;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Getter
    @NoArgsConstructor
    public static class KakaoAccount {
        private String email;

        private Profile profile;

        @Getter
        @NoArgsConstructor
        public static class Profile {
            private String nickname;

            @JsonProperty("thumbnail_image_url")
            private String thumbnailImageUrl;

            @JsonProperty("profile_image_url")
            private String profileImageUrl;
        }
    }
}