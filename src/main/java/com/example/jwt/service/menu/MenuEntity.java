package com.example.jwt.service.menu;

import com.example.jwt.config.base.BaseEntity;
import com.example.jwt.config.constant.EntitiyConstant;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(
        name = "Menus",
        indexes = {
                @Index(name = "idx_menus_search", columnList =  "name, createdAt")
        }
)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuEntity extends BaseEntity {

    private String name;
    
    private String description;

    private String icon;

    private String url;

    private String restricted;

    @Override
    public String getEntityType() {
        return EntitiyConstant.MENU_ENTITY.getValue();
    }
}
