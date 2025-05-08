package com.example.jwt.service.geo;

import com.example.jwt.service.config.ConfigEntity;
import com.example.jwt.service.config.ConfigVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class GeoVO {

    private String UID;
    private String countryName;
    private String countryCode;
    private String longitude;
    private String latitude;
    private String creatorName;
    private LocalDate createdAt;
    private String notes;

    public static GeoVO toGeoVO(GeoEntity entity) {
        return GeoVO.builder()
                .UID(entity.getUID())
                .countryName(entity.getCountryName())
                .countryCode(entity.getCountryCode())
                .longitude(entity.getLongitude())
                .latitude(entity.getLatitude())
                .creatorName(entity.getCreator().getName())
                .createdAt(entity.getCreatedAt().toLocalDate())
                .notes(entity.getNotes())
                .build();
    }
}
