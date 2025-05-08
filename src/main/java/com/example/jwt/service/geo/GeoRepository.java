package com.example.jwt.service.geo;

import com.example.jwt.service.config.ConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GeoRepository extends JpaRepository<GeoEntity, String> {
    List<GeoEntity> getGeoEntitiesByCreatorUID(String creatorUID);
}
