package com.example.jwt.web.ConfigController;

import com.example.jwt.config.jwt.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/config")
public class ConfigController {

    @GetMapping("/getConfig")
    public ResponseEntity<Map<String, Object>> getConfig() {
        return ResponseEntity.ok(
                Map.of(
                        "liveAccessToken", JwtTokenProvider.ACCESS_TIME_TO_LIVE,
                        "liveRefreshToken", JwtTokenProvider.REFRESH_TIME_TO_LIVE
                )
        );
    }

}
