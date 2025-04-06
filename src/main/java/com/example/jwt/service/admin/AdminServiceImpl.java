package com.example.jwt.service.admin;

import com.example.jwt.service.user.UserRepository;
import com.example.jwt.service.user.UserVO;
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
    public Map<String, Object> approveUser(List<UserVO> users) {
        List<String> approved = new ArrayList<>();
        List<String> failed = new ArrayList<>();

        for (UserVO user : users) {
            userRepository.findById(user.getUserId()).ifPresentOrElse(
                    userEntity -> {
                        userEntity.approve();
                        userEntity.changeRole(user.getRole());
                        userRepository.save(userEntity);
                        approved.add(userEntity.getUserId());
                    },
                    () -> failed.add(user.getUserId())
            );
        }

        return Map.of("approved", approved
                , "failed", failed);
    }
}
