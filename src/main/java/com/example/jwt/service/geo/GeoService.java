package com.example.jwt.service.geo;

import java.util.List;

public interface GeoService {

    void create(GeoVO geo);

    List<GeoVO> getList();

    void delete(String uid);
}
