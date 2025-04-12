package com.example.jwt.service.menu;

import java.util.List;

public interface MenuService {

    public void create(MenuVO menu);

    public List<MenuVO> getMain();

    public List<MenuVO> get(String uid);
}
