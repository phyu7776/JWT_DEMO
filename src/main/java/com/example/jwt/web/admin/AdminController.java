package com.example.jwt.web.admin;

import com.example.jwt.service.admin.AdminService;
import com.example.jwt.service.user.UserService;
import com.example.jwt.service.user.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final UserService userService;

    @PutMapping("/approve")
    public ResponseEntity<Map<String, Object>> approveUser(@RequestBody List<UserVO> users) {
        return ResponseEntity.ok(adminService.approveUser(users));
    }

    @GetMapping("/getUsers")
    public ResponseEntity<List<UserVO>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PatchMapping("/update")
    public ResponseEntity<UserVO> updateUser(@RequestBody UserVO users) {
        return ResponseEntity.ok(userService.updateUser(users));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUser(@RequestBody List<UserVO> users) {
        userService.deleteUser(users);
        return ResponseEntity.noContent().build();
    }
}
