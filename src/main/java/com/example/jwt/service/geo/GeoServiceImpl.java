package com.example.jwt.service.geo;

import com.example.jwt.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeoServiceImpl implements GeoService{

    private final GeoRepository geoRepository;

    @Override
    public void create(GeoVO geo) {
        geoRepository.save(GeoEntity.builder()
                .countryName(geo.getCountryName())
                .countryCode(geo.getCountryCode())
                .latitude(geo.getLatitude())
                .longitude(geo.getLongitude())
                .notes(geo.getNotes())
                .creatorUID(SecurityUtil.getCurrentUser().getUID())
            .build());
    }

    @Override
    public List<GeoVO> getList() {
        List<GeoEntity> geoList = geoRepository.getGeoEntitiesByCreatorUID(SecurityUtil.getCurrentUser().getUID());
        return geoList.stream().map(GeoVO::toGeoVO).toList();
    }

    @Override
    public void delete(String uid) {
        geoRepository.deleteById(uid);
    }
}
