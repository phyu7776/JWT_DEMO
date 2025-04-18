package com.example.jwt.service.menu;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<MenuEntity, String> {

    boolean existsByName(String name);

    List<MenuEntity> findByParentUIDIsNullOrderByMenuOrderAscNameDesc();

    List<MenuEntity> findByParentUIDOrderByMenuOrderAscNameDesc(String uid);
}
