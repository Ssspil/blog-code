package com.aoxx.security.jwt;



import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;


@Slf4j
@Component
public class JwtHelper {
    private static final int ACCESS_TOKEN_VALIDITY = 30 * 60 * 1000;    // 30분

    @Value("${spring.security.jwt.secret}")
    private String secretKey;

    // 토큰 생성
    public String generateAccessToken(String email, String role){
        return JWT.create()
                .withSubject(email)
                .withClaim("role", role)
                .withIssuedAt(new Date(System.currentTimeMillis()))     // 토큰 발급 시간
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY))    // 토큰 만료 시간
                .sign(Algorithm.HMAC512(secretKey));
    }

    // 토큰 유효성 체크
    public boolean validation(String token){
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC512(secretKey)).build();
            verifier.verify(token);
        } catch (JWTVerificationException ex){  // 변조 했거나 만료 되었으면 예외
            return false;
        }
        return true;
    }

    // 토큰에서 주체 추출
    public String extractSubject(String token){
        return JWT.decode(token)
                .getSubject();
    }

    // 토큰에서 유효시간 추출
    public long extractExpiredAt(String token){
        return JWT.decode(token)
                .getExpiresAt()
                .getTime();
    }

    // 토큰에서 권한 추출
    public String extractRole(String token){
        return JWT.decode(token)
                .getClaim("role")
                .asString();
    }



}
