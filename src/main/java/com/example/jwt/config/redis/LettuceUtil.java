package com.example.jwt.config.redis;

import com.example.jwt.config.constant.ObjectConstant;
import com.example.jwt.config.jwt.JwtTokenProvider;
import com.example.jwt.service.user.UserVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LettuceUtil {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    public void saveJWT(Map<String, String> token, UserVO user) {
        saveAccessToken(token.get(ObjectConstant.ACCESS_TOKEN), user);
        saveRefreshToken(token.get(ObjectConstant.REFRESH_TOKEN), user);
    }

    public void saveAccessToken(String accessToken, UserVO user) {
        redisTemplate.opsForHash().putAll(accessToken, objectMapper.convertValue(user, Map.class));
        redisTemplate.expire(accessToken, Duration.ofMillis(JwtTokenProvider.ACCESS_TIME_TO_LIVE));
    }

    private void saveRefreshToken(String refreshToken, UserVO user) {
        redisTemplate.opsForValue().set(refreshToken, user.getUserId(), Duration.ofMillis(JwtTokenProvider.REFRESH_TIME_TO_LIVE));
        redisTemplate.expire(refreshToken, Duration.ofMillis(JwtTokenProvider.REFRESH_TIME_TO_LIVE));
    }

    public UserVO getUser(String token) {
        return objectMapper.convertValue(redisTemplate.opsForHash().entries(token), UserVO.class);
    }

    public String getRefreshToken(String refreshToken) {
        return String.valueOf(redisTemplate.opsForValue().get(refreshToken));
    }

    public void deleteTokens(Map<String, String> tokens) {
        Optional.ofNullable(tokens.get(ObjectConstant.ACCESS_TOKEN))
                .ifPresent(redisTemplate::delete);

        Optional.ofNullable(tokens.get(ObjectConstant.REFRESH_TOKEN))
                .ifPresent(redisTemplate::delete);
    }

    public void blockToken(String token) {
        redisTemplate.opsForValue().set(token, "BLOCK_TOKEN", Duration.ofMillis(JwtTokenProvider.REFRESH_TIME_TO_LIVE));
    }
}
