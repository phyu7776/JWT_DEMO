package com.example.jwt.service.menu;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService{

    private final MenuRepository menuRepository;

    @Override
    public void create(MenuVO menu) {
        menuRepository.save(MenuEntity.builder()
                .name(menu.getName())
                .description(menu.getDescription())
                .icon(menu.getIcon())
                .url(menu.getUrl())
                .restricted(menu.getRestricted())
                .build());
    }

    @Override
    public List<MenuVO> getMenu() {
        List<MenuEntity> menuList = menuRepository.findAllByOrderByNameDesc();
        return menuList.stream().map(MenuVO::toMenuVO).toList();
    }
}
