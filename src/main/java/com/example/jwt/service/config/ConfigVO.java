package com.example.jwt.service.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ConfigVO {

    String type;
    String configValue;
    String name;
    String subType;

    public static ConfigVO toConfigVO(ConfigEntity entity) {
        return ConfigVO.builder()
                .type(entity.getType())
                .configValue(entity.getConfigValue())
                .name(entity.getName())
                .subType(entity.getSubType())
                .build();
    }
}
