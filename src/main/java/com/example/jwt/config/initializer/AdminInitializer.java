package com.example.jwt.config.initializer;

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
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.id}")
    private String userId;

    @Value("${admin.password}")
    private String password;

    @Override
    public void run(String... args) throws Exception {

        UserEntity entity = UserEntity.builder()
                .userId(userId)
                .password(passwordEncoder.encode(password))
                .name("admin")
                .nickname("admin")
                .role(UserVO.role.ADMIN.getRole())
                .birthDate(LocalDate.now())
                .build();

        entity.approve();

        userRepository.save(entity);
    }
}
