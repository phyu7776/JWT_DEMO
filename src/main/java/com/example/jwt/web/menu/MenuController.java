package com.example.jwt.web.menu;

import com.example.jwt.service.menu.MenuService;
import com.example.jwt.service.menu.MenuVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody MenuVO menu) {
        menuService.create(menu);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/get")
    public ResponseEntity<List<MenuVO>> getMenu() {
        return ResponseEntity.ok(menuService.getMenu());
    }
}
