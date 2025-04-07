package com.example.jwt.utils;

import com.example.jwt.service.user.UserVO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    private SecurityUtil() {};

    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static UserVO getCurrentUser() {
        Authentication authentication = getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null; // 인증 안 됐을 때
        }

        return (UserVO) authentication.getPrincipal();
    }
}

