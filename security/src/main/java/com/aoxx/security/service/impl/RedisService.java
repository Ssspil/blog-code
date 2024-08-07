package com.aoxx.security.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    // 값 넣기
    public void save(String key, String value, Duration ttl){
        redisTemplate.opsForValue().set(key, value, ttl);
    }

    // 값 가져오기
    public Optional<String> find(String key){
        return Optional.ofNullable(redisTemplate.opsForValue().get(key));
    }

    // 값 삭제하기
    public void delete(String key){
        redisTemplate.delete(key);
    }
}
