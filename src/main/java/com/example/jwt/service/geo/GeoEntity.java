package com.example.jwt.service.geo;

import com.example.jwt.config.base.BaseEntity;
import com.example.jwt.config.constant.EntitiyConstant;
import com.example.jwt.service.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Table(
        name = "geo"
//        , indexes = {
//                @Index(name = "idx_geo_search", columnList =  "type, subType")
//        }
)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeoEntity extends BaseEntity {

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "country_code")
    private String countryCode;

    private String longitude;

    private String latitude;

    private String notes;

    @Column(name = "creator_uid")
    private String creatorUID;

    @ManyToOne(fetch = FetchType.LAZY) // ✅ 연관관계 매핑
    @JoinColumn(name = "creator_uid", referencedColumnName = "uid", insertable = false, updatable = false)
    private UserEntity creator; // ✅ creator_uid 로 연결된 UserEntity

    @Override
    public String getEntityType() {
        return EntitiyConstant.GEO_ENTITY.getValue();
    }
}
