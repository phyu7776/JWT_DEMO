package com.example.jwt.service.user;

import com.example.jwt.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public String login(UserVO user) {
        return jwtTokenProvider.generateToken(user.getUserId());
    }
}
