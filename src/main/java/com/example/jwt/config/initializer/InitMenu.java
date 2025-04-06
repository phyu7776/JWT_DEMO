package com.example.jwt.config.initializer;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InitMenu {
    private String name;
    private String url;
    private String restrict;
    private int order;
}
