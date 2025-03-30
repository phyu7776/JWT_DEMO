package com.example.jwt.web.admin;

import com.example.jwt.config.excetion.APIException;
import com.example.jwt.service.user.UserEntity;
import com.example.jwt.service.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin")
public class AdminContorller {
    private final UserRepository userRepository;

    @PutMapping("/approve/{userId}")
    public ResponseEntity<?> approveUser(@PathVariable String userId) {
    UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new APIException(APIException.ErrorCode.USER_INFO_INVALID));

    user.approve();
    userRepository.save(user);
    
    return ResponseEntity.ok("승인 완료");
    }
}
