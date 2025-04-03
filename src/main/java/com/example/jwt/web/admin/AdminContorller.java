package com.example.jwt.web.admin;

import com.example.jwt.config.excetion.APIException;
import com.example.jwt.service.admin.AdminService;
import com.example.jwt.service.user.UserEntity;
import com.example.jwt.service.user.UserRepository;
import com.example.jwt.service.user.UserService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin")
public class AdminContorller {

    private final AdminService adminService;

    @PutMapping("/approve/{userId}")
    public ResponseEntity<?> approveUser(@RequestBody List<String> userIds) {
        return ResponseEntity.ok(adminService.approveUser(userIds));
    }
}
