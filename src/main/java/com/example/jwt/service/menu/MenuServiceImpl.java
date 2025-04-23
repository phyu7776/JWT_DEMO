package com.example.jwt.service.menu;

import com.example.jwt.config.excetion.APIException;
import com.example.jwt.service.menu.repository.MenuRepository;
import com.example.jwt.service.user.UserVO;
import com.example.jwt.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        List<MenuVO> menuList = menuRepository.getMenusByParentUID(null);

        UserVO user = SecurityUtil.getCurrentUser();

        return menuList.stream()
                .filter(menu -> ObjectUtils.isEmpty(menu.getRestricted()) || menu.getRestricted().contains(user.getRole()))
                .toList();
    }

    public List<MenuVO> get(String uid) {
        return menuRepository.getMenusByParentUID(uid);
    }

    public List<MenuVO> getFullMenuTree() {
        return buildMenuTree(menuRepository.findAll(Sort.by("menuOrder").ascending()).stream()
                .map(MenuVO::toMenuVO)
                .collect(Collectors.toList()), null); // parentUid == null부터 시작
    }

    private List<MenuVO> buildMenuTree(List<MenuVO> flatList, String parentUid) {
        return flatList.stream()
                .filter(menuVO -> Objects.equals(menuVO.getParentUid(), parentUid))
                .peek(menuVO -> menuVO.setChildren(buildMenuTree(flatList, menuVO.getUid())))
                .collect(Collectors.toList());
    }
}
