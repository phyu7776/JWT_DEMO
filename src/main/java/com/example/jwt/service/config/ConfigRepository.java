package com.example.jwt.service.config;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConfigRepository extends JpaRepository<ConfigEntity, String> {

    List<ConfigEntity> getConfigEntitiesByTypeAndSubType(String type, String subType);

    List<ConfigEntity> getConfigEntitiesByType(String type);
}
