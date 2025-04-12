package com.example.jwt.service.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConfigServiceImpl implements ConfigService {

    private final ConfigRepository configRepository;

    @Override
    public ConfigVO createConfig(ConfigVO config) {

        ConfigEntity configEntity = configRepository.save(ConfigEntity.builder()
                        .type(config.getType())
                        .configValue(config.getConfigValue())
                        .subType(config.getSubType())
                .build());

        return ConfigVO.toConfigVO(configEntity);
    }

    @Override
    public List<ConfigVO> getConfig(String type, String subType) {

        List<ConfigEntity> configs = new ArrayList<>();

        if (ObjectUtils.isEmpty(subType)) {
            configs = configRepository.getConfigEntitiesByType(type);
        } else {
            configs = configRepository.getConfigEntitiesByTypeAndSubType(type, subType);
        }

        return configs.stream().map(ConfigVO::toConfigVO).toList();
    }
}
