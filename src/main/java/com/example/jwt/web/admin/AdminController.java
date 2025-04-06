package com.example.jwt.web.admin;

import com.example.jwt.service.admin.AdminService;
import com.example.jwt.service.user.UserService;
import com.example.jwt.service.user.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final UserService userService;

    @PutMapping("/approve/{userId}")
    public ResponseEntity<?> approveUser(@RequestBody List<UserVO> users) {
        return ResponseEntity.ok(adminService.approveUser(users));
    }

    @GetMapping("/getUsers")
    public ResponseEntity<List<UserVO>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
