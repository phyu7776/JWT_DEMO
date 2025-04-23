package com.example.jwt.service.menu;

import java.util.List;

public interface MenuService {

    void create(MenuVO menu);

    List<MenuVO> getMain();

    List<MenuVO> get(String uid);

    List<MenuVO> getFullMenuTree();
}
