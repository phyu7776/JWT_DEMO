package com.example.jwt.service.admin;

import com.example.jwt.service.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    @Override
    public Map<String, Object> approveUser(List<String> userIds) {
        List<String> approved = new ArrayList<>();
        List<String> failed = new ArrayList<>();

        for (String userId : userIds) {
            userRepository.findById(userId).ifPresentOrElse(
                    user -> {
                        user.approve();
                        userRepository.save(user);
                        approved.add(userId);
                    },
                    () -> failed.add(userId)
            );
        }

        return Map.of("approved", approved
                , "failed", failed);
    }
}
