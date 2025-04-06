package com.example.jwt.service.menu;

import java.util.List;

public interface MenuService {

    public void create(MenuVO menu);

    public List<MenuVO> getMenu();
}
