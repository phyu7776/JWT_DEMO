package com.example.jwt.web.admin;

import com.example.jwt.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
