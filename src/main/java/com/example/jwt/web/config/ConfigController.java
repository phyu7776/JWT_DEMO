package com.example.jwt.web.config;

import com.example.jwt.config.constant.ObjectConstant;
import com.example.jwt.config.jwt.JwtTokenProvider;
import com.example.jwt.service.config.ConfigRepository;
import com.example.jwt.service.config.ConfigService;
import com.example.jwt.service.config.ConfigVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/config")
public class ConfigController {

    private final ConfigService configService;
    private final ConfigRepository configRepository;

    @PostMapping("/create")
    public ResponseEntity<ConfigVO> createConfig(@RequestBody ConfigVO config) {
        return ResponseEntity.ok(configService.createConfig(config));
    }

    @GetMapping("/get/{type}/{subType}")
    public ResponseEntity<List<ConfigVO>> getConfig(
            @PathVariable String type,
            @PathVariable(required = false) String subType) {
        return ResponseEntity.ok(configService.getConfig(type, subType));
    }

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
