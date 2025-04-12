package com.example.jwt.config.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EntitiyConstant {
    USER_ENTITY("userEntity"),
    MENU_ENTITY("menuEntity"),
    CONFIG_ENTITY("configEntity"),
    ;

    public final String value;
}
