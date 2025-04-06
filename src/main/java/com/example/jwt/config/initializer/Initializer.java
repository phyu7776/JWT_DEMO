package com.example.jwt.config.initializer;

import com.example.jwt.service.menu.MenuEntity;
import com.example.jwt.service.menu.MenuRepository;
import com.example.jwt.service.user.UserEntity;
import com.example.jwt.service.user.UserRepository;
import com.example.jwt.service.user.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Configuration
@RequiredArgsConstructor
public class Initializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MenuRepository menuRepository;

    @Value("${admin.id}")
    private String userId;

    @Value("${admin.password}")
    private String password;

    private final String[] initMenu = {"강습 예약", "투어 신청", "증고 매물", "공구", "게시판", "로그북", "시스템 설정"};

    @Override
    public void run(String... args) throws Exception {
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
        for (String menu : initMenu) {
            MenuEntity entity = MenuEntity.builder()
                    .name(menu)
                    .build();

            menuRepository.save(entity);
        }
    }
}
