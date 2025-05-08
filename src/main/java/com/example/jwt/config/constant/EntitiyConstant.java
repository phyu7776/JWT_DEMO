package com.example.jwt.config.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EntitiyConstant {
    USER_ENTITY("userEntity"),
    MENU_ENTITY("menuEntity"),
    CONFIG_ENTITY("configEntity"),
    BOARD_ENTITY("boardEntity"),
    GEO_ENTITY("geoEntity")
    ;

    public final String value;
}
