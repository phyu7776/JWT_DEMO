package com.example.jwt.service.menu.repository;

import com.example.jwt.service.menu.MenuVO;

import java.util.List;

public interface MenuRepositoryCustom {
    List<MenuVO> getMenusByParentUID(String parentUID);
}
