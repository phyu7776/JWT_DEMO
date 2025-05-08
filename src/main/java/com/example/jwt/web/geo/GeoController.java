package com.example.jwt.web.geo;

import com.example.jwt.service.geo.GeoService;
import com.example.jwt.service.geo.GeoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/geo")
public class GeoController {

    private final GeoService geoService;

    @PostMapping("/create")
    public ResponseEntity<Void> createConfig(@RequestBody GeoVO geo) {
        geoService.create(geo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getList")
    public ResponseEntity<List<GeoVO>> getList() {
        return ResponseEntity.ok(geoService.getList());
    }

    @DeleteMapping("/delete/{uid}")
    public ResponseEntity<Void> delete(@PathVariable String uid) {
        geoService.delete(uid);
        return ResponseEntity.noContent().build();
    }
}
