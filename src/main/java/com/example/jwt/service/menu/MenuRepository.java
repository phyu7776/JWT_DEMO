package com.example.jwt.service.menu;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<MenuEntity, String> {

    List<MenuEntity> findAllByOrderByNameDesc();
}
