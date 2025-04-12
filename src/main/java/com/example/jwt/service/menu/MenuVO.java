package com.example.jwt.service.menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MenuVO {

    private String UID;

    private String name;

    private String description;

    private String icon;

    private String url;

    private String restricted;

    private String parentUID;

    public static MenuVO toMenuVO(MenuEntity entity) {
        return MenuVO.builder()
                .UID(entity.getUID())
                .name(entity.getName())
                .description(entity.getDescription())
                .icon(entity.getIcon())
                .url(entity.getUrl())
                .restricted(entity.getRestricted())
                .parentUID(entity.getParentUID())
                .build();
    }
}
