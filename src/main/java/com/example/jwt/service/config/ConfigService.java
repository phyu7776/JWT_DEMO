package com.example.jwt.service.config;

import java.util.List;

public interface ConfigService {

    ConfigVO createConfig(ConfigVO config);

    List<ConfigVO> getConfig(String type, String subType);

}
