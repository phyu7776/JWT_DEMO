package com.example.jwt.service.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    boolean existsByUserId(String userid);

    Optional<UserEntity> findByUserId(String userid);

    List<UserEntity> findAllByOrderByCreatedAtDesc();

    Optional<UserEntity> findByUID(String uid);
}
