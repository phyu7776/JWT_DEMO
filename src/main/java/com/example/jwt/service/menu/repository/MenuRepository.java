package com.example.jwt.service.menu.repository;

import com.example.jwt.service.menu.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<MenuEntity, String>, MenuRepositoryCustom {

    boolean existsByName(String name);
}
