package com.example.jwt.config.redis;

import com.example.jwt.config.jwt.JwtTokenProvider;
import com.example.jwt.service.user.UserVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class LettuceUtil {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    public void saveJWT(String token, UserVO user) {
        redisTemplate.opsForHash().putAll(token, objectMapper.convertValue(user, Map.class));
        redisTemplate.expire(token, Duration.ofMillis(JwtTokenProvider.TIME_TO_LIVE));
    }

    public UserVO getUser(String token) {
        return objectMapper.convertValue(redisTemplate.opsForHash().entries(token), UserVO.class);
    }
}
