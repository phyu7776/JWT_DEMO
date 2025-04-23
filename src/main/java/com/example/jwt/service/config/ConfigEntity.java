package com.example.jwt.service.config;

import com.example.jwt.config.base.BaseEntity;
import com.example.jwt.config.constant.EntitiyConstant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(
        name = "config",
        indexes = {
                @Index(name = "idx_config_search", columnList =  "type, subType")
        }
)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfigEntity extends BaseEntity {

    private String type;

    @Column(name="config_value")
    private String configValue;

    @Column(name="sub_type")
    private String subType;

    private String name;

    @Override
    public String getEntityType() {
        return EntitiyConstant.CONFIG_ENTITY.getValue();
    }
}
