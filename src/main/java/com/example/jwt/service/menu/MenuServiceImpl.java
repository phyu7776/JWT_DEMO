package com.example.jwt.service.menu;

import com.example.jwt.config.excetion.APIException;
import com.example.jwt.service.user.UserVO;
import com.example.jwt.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService{

    private final MenuRepository menuRepository;

    @Override
    public void create(MenuVO menu) {
        if (menuRepository.existsByName(menu.getName())) {
            throw new APIException(APIException.ErrorCode.DUPLICATE_MENU);
        }

        menuRepository.save(MenuEntity.builder()
                .name(menu.getName())
                .description(menu.getDescription())
                .icon(menu.getIcon())
                .url(menu.getUrl())
                .restricted(menu.getRestricted())
                .build());
    }

    @Override
    public List<MenuVO> getMain() {
        List<MenuEntity> menuList = menuRepository.findByParentUIDIsNullOrderByMenuOrderAscNameDesc();

        UserVO user = SecurityUtil.getCurrentUser();

        return menuList.stream()
                .map(MenuVO::toMenuVO)
                .filter(menu -> ObjectUtils.isEmpty(menu.getRestricted()) || menu.getRestricted().contains(user.getRole()))
                .toList();

    }

    public List<MenuVO> get(String uid) {
        List<MenuEntity> menuList = menuRepository.findByParentUIDOrderByMenuOrderAscNameDesc(uid);
        return menuList.stream().map(MenuVO::toMenuVO).toList();
    }
}
