package com.example.jwt.config.initializer;

import com.example.jwt.config.constant.ObjectConstant;
import com.example.jwt.service.config.ConfigEntity;
import com.example.jwt.service.config.ConfigRepository;
import com.example.jwt.service.config.ConfigService;
import com.example.jwt.service.config.ConfigVO;
import com.example.jwt.service.menu.MenuEntity;
import com.example.jwt.service.menu.MenuRepository;
import com.example.jwt.service.menu.MenuVO;
import com.example.jwt.service.user.UserEntity;
import com.example.jwt.service.user.UserRepository;
import com.example.jwt.service.user.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class Initializer {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;
    private final ConfigRepository configRepository;

    @Value("${admin.id}")
    private String userId;

    @Value("${admin.password}")
    private String password;

    private final List<MenuVO> defaultMenus = new ArrayList<>();

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        createAdminAccount();
        createDefaultMenus();
        createDefaultSubMenus();
        createDefaultRole();
    }

    private void createAdminAccount() {
        if (!userRepository.existsByUserId(userId)) {
            UserEntity entity = UserEntity.builder()
                    .userId(userId)
                    .password(passwordEncoder.encode(password))
                    .name(userId)
                    .nickname(userId)
                    .role(UserVO.ROLE.ADMIN.getRole())
                    .birthDate(LocalDate.now())
                    .build();

            entity.approve();

            userRepository.save(entity);
        }
    }

    private void createDefaultMenus() {
        InitMenu[] initMenus = {
                new InitMenu("시스템 설정", "/system-settings",
                        UserVO.ROLE.ADMIN.getRole() + "," + UserVO.ROLE.SUPERVISOR.getRole(), "", 0),
                new InitMenu("게시판", "/board", "", "", 1),
                new InitMenu("강습 예약", "/lesson-reservation", "", "", 2),
                new InitMenu("투어 신청", "/tour-application", "", "",3),
                new InitMenu("중고 매물", "/used-market", "", "",4),
                new InitMenu("공구", "/co-purchase", "", "", 5),
                new InitMenu("로그북", "/logbook", "", "", 6)
        };

        for (InitMenu menu : initMenus) {
            if (!menuRepository.existsByName(menu.getName())) {
                MenuEntity entity = MenuEntity.builder()
                        .name(menu.getName())
                        .url(menu.getUrl())
                        .restricted(menu.getRestrict())
                        .menuOrder(menu.getOrder())
                        .build();

                defaultMenus.add(MenuVO.toMenuVO(menuRepository.save(entity)));
            }
        }
    }

    private void createDefaultSubMenus() {
        List<InitMenu> initMenus = new ArrayList<>();

        for (MenuVO defaultMenu : defaultMenus) {
            if ("시스템 설정".equals(defaultMenu.getName())) {
                initMenus.add(new InitMenu("유저 관리", "/manage-user", UserVO.ROLE.ADMIN.getRole(), defaultMenu.getUID(), 0));
            }
        }

        for (InitMenu menu : initMenus) {
            MenuEntity entity = MenuEntity.builder()
                    .name(menu.getName())
                    .url(menu.getUrl())
                    .restricted(menu.getRestrict())
                    .parentUID(menu.getParentMenu())
                    .menuOrder(menu.getOrder())
                    .build();

            menuRepository.save(entity);
        }
    }

    private void createDefaultRole() {
        ConfigVO[] configVOs = {
                ConfigVO.builder().type(ObjectConstant.SYSTEM_CONFIG).configValue(UserVO.ROLE.ADMIN.getRole()).name("시스템 관리자").subType(ObjectConstant.ROLE).build(),
                ConfigVO.builder().type(ObjectConstant.SYSTEM_CONFIG).configValue(UserVO.ROLE.SUPERVISOR.getRole()).name("관리자").subType(ObjectConstant.ROLE).build(),
                ConfigVO.builder().type(ObjectConstant.SYSTEM_CONFIG).configValue(UserVO.ROLE.USER.getRole()).name("유저").subType(ObjectConstant.ROLE).build()
        };

        for (ConfigVO config : configVOs) {
            ConfigEntity entity = ConfigEntity.builder()
                    .type(config.getType())
                    .subType(config.getSubType())
                    .name(config.getName())
                    .configValue(config.getConfigValue())
                    .build();

            configRepository.save(entity);
        }
    }
}
