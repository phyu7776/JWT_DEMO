package com.example.jwt.service.menu;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(
        name = "Menus"
)
public class MenuEntity {

    @Id
    private String UID;

    private String name;
    
    private String description;

    private String icon;

    private String url;
}
