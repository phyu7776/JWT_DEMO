package com.example.jwt.config.jwt;

import com.example.jwt.utils.LettuceUtil;
import com.example.jwt.service.user.UserVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component
public class JwtTokenProvider {

    public static final long ACCESS_TIME_TO_LIVE = 60 * 60 * 1000L;
    public static final long REFRESH_TIME_TO_LIVE = 1000L * 60 * 60 * 24 * 14;
    private final LettuceUtil lettuceUtil;

    // jwt 암호를 하기위한 비밀키
    @Value("${jwt.secret}")
    private String secretKey;

    private Key key;

    public JwtTokenProvider(LettuceUtil lettuceUtil) {
        this.lettuceUtil = lettuceUtil;
    }

    @PostConstruct
    protected void init() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public Map<String, String> generateToken(String userId, String role) {
        return Map.of(
                "accessToken", generateAccessToken(userId, role),
                "refreshToken", generateRefreshToken(userId)
        );
    }

    // 토큰 생성
    public String generateAccessToken(String userId, String role) {
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("role", role);

        Date now = new Date();
        Date expireTime = new Date(now.getTime() + ACCESS_TIME_TO_LIVE);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expireTime)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    private String generateRefreshToken(String userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + REFRESH_TIME_TO_LIVE);

        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 토큰에서 사용자 ID 추출
    public String getUserId(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // 사용자 role 추출
    public String getRole(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }

    // 토큰 검증
    public boolean validateToken(String token) {

        Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

        // 토큰이 accessToken 인지 refreshToken 인지 구분
        Map<Object, Object> entries = null;
        try {
            entries = lettuceUtil.getHashMap(token);
        } catch (Exception e) {
            log.error("Invalid token");
        }

        if (!ObjectUtils.isEmpty(entries)) {
            // accessToken
            UserVO user = lettuceUtil.getUser(token);

            if (ObjectUtils.isEmpty(user.getUID())) {
                throw new JwtException("Invalid token");
            }
        } else {
            // refreshToken
            Object checkToken = lettuceUtil.getRefreshToken(token);

            if (ObjectUtils.isEmpty(checkToken) || "BLOCK_TOKEN".equals(checkToken.toString())) {
                throw new JwtException("Invalid token");
            }
        }

        return true;
    }


    // 요청 헤더에서 토큰 가져오기
    public String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (!ObjectUtils.isEmpty(token)) {
            return token.replace("Bearer ", "");
        }

        return null;
    }
}
