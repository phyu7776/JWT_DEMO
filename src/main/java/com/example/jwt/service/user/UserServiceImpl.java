package com.example.jwt.service.user;

import com.example.jwt.config.excetion.APIException;
import com.example.jwt.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signup(UserVO user) {
        if (userRepository.existsByUserId(user.getUserId())) {
            throw new APIException(APIException.ErrorCode.DUPLICATE_USER);
        }

        UserEntity userEntity = UserEntity.builder()
                .userId(user.getUserId())
                .password(passwordEncoder.encode(user.getPassword()))
                .role(UserVO.role.USER.getRole())
                .build();

        userRepository.save(userEntity);
    }

    @Override
    public String login(UserVO user) {
        UserEntity userEntity = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new APIException(APIException.ErrorCode.USER_INFO_INVALID));

        if (!passwordEncoder.matches(user.getPassword(), userEntity.getPassword())) {
            throw new APIException(APIException.ErrorCode.INVALID_PASSWORD);
        }

        if (!userEntity.isApproved()){
            throw new APIException(APIException.ErrorCode.USER_NOT_APPROVED);
        }

        return jwtTokenProvider.generateToken(userEntity.getUserId(), userEntity.getRole());
    }
}
