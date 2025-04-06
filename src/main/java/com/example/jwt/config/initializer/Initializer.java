package com.example.jwt.config.initializer;

import com.example.jwt.service.menu.MenuEntity;
import com.example.jwt.service.menu.MenuRepository;
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

@Configuration
@RequiredArgsConstructor
public class Initializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MenuRepository menuRepository;

    @Value("${admin.id}")
    private String userId;

    @Value("${admin.password}")
    private String password;

    private final InitMenu[] initMenus = {
            new InitMenu("시스템 설정", "/system-settings", UserVO.role.ADMIN.getRole(), 0),
            new InitMenu("게시판", "/board", "",1),
            new InitMenu("강습 예약", "/lesson-reservation", "", 2),
            new InitMenu("투어 신청", "/tour-application", "", 3),
            new InitMenu("중고 매물", "/used-market", "", 4),
            new InitMenu("공구", "/co-purchase", "", 5),
            new InitMenu("로그북", "/logbook", "", 6)
    };

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        createAdminAccount();
        createDefaultMenus();
    }

    private void createAdminAccount() {
        if (!userRepository.existsByUserId(userId)) {
            UserEntity entity = UserEntity.builder()
                    .userId(userId)
                    .password(passwordEncoder.encode(password))
                    .name(userId)
                    .nickname(userId)
                    .role(UserVO.role.ADMIN.getRole())
                    .birthDate(LocalDate.now())
                    .build();

            entity.approve();

            userRepository.save(entity);
        }
    }

    private void createDefaultMenus() {
        for (InitMenu menu : initMenus) {
            if (!menuRepository.existsByName(menu.getName())) {
                MenuEntity entity = MenuEntity.builder()
                        .name(menu.getName())
                        .url(menu.getUrl())
                        .restricted(menu.getRestrict())
                        .menuOrder(menu.getOrder())
                        .build();

                menuRepository.save(entity);
            }
        }
    }
}
