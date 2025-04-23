package com.example.jwt.service.menu;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuVO {

    private String uid;

    private String name;

    private String description;

    private String icon;

    private String url;

    private String restricted;

    private String parentUid;

    private boolean hasChildren;

    private List<MenuVO> children = new ArrayList<>(); //재귀 트리용 필드

    public static MenuVO toMenuVO(MenuEntity entity) {
        return MenuVO.builder()
                .uid(entity.getUID())
                .name(entity.getName())
                .description(entity.getDescription())
                .icon(entity.getIcon())
                .url(entity.getUrl())
                .restricted(entity.getRestricted())
                .parentUid(entity.getParentUID())
                .build();
    }
}
