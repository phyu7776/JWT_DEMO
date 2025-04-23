package com.example.jwt.service.menu.repository;

import com.example.jwt.service.menu.MenuEntity;
import com.example.jwt.service.menu.MenuVO;
import com.example.jwt.service.menu.QMenuEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MenuRepositoryImpl implements MenuRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<MenuVO> getMenusByParentUID(String parentUID) {
        QMenuEntity menuEntity = QMenuEntity.menuEntity;
        QMenuEntity child = new QMenuEntity("child");

        return queryFactory
                .select(Projections.fields(MenuVO.class,
                        menuEntity.UID.as("uid"),
                        menuEntity.name,
                        menuEntity.description,
                        menuEntity.icon,
                        menuEntity.url,
                        menuEntity.restricted,
                        menuEntity.parentUID.as("parentUid"),
                        JPAExpressions.selectOne()
                                .from(child)
                                .where(child.parentUID.eq(menuEntity.UID))
                                .exists().as("hasChildren")
                ))
                .from(menuEntity)
                .where(
                        ObjectUtils.isEmpty(parentUID) ?
                                menuEntity.parentUID.isNull()
                                : menuEntity.parentUID.eq(parentUID))
                .orderBy(menuEntity.menuOrder.asc(), menuEntity.name.desc())
                .fetch();
    }
}
