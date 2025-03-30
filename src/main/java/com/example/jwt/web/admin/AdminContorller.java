package com.example.jwt.web.admin;

import com.example.jwt.config.excetion.APIException;
import com.example.jwt.service.user.UserEntity;
import com.example.jwt.service.user.UserRepository;
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
    private final UserRepository userRepository;

    @PutMapping("/approve/{userId}")
    public ResponseEntity<?> approveUser(@RequestBody List<String> userIds) {

        List<String> approved = new ArrayList<>();
        List<String> failed = new ArrayList<>();

        for(String userId: userIds) {
            userRepository.findById(userId).ifPresentOrElse(
                    user -> {
                        user.approve();
                        userRepository.save(user);
                        approved.add(userId);
                    },
                    () -> failed.add(userId)
            );
        }
    
    return ResponseEntity.ok(Map.of(
            "approved", approved,
            "failed", failed
    ));
        
    }
}
