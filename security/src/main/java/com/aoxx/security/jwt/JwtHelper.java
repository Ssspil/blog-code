package com.aoxx.security.jwt;



import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;


@Slf4j
@Component
public class JwtHelper {
    private static final int ACCESS_TOKEN_VALIDITY = 30 * 60 * 1000;

    @Value("${spring.security.jwt.secret}")
    private String secretKey;

    // [4] security 로그인 성공 했을 때 토큰 생성
    public String generateAccessToken(String email, String role){
        return JWT.create()
                .withSubject(email)
                .withClaim("role", role)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY))
                .sign(Algorithm.HMAC512(secretKey));
    }

}
