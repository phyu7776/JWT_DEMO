package com.example.jwt.service.user;

import com.example.jwt.config.excetion.APIException;
import com.example.jwt.config.jwt.JwtTokenProvider;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Override
    public void signup(UserVO user) {
        if (userRepository.existsByUserId(user.getUserId())) {
            throw new APIException(APIException.ErrorCode.DUPLICATE_USER);
        }

        UserEntity userEntity = UserEntity.builder()
                .userId(user.getUserId())
                .password(user.getPassword())
                .build();

        userRepository.save(userEntity);
    }

    @Override
    public String login(UserVO user) {
        UserEntity userEntity = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new APIException(APIException.ErrorCode.USER_INFO_INVALID));

        if (!userEntity.getPassword().equals(user.getPassword())) {
            throw new APIException(APIException.ErrorCode.INVALID_PASSWORD);
        }

        return jwtTokenProvider.generateToken(user.getUserId());
    }
}
