package com.example.jwt.service.user;

import com.example.jwt.config.constant.ObjectConstant;
import com.example.jwt.config.excetion.APIException;
import com.example.jwt.config.jwt.JwtTokenProvider;
import com.example.jwt.config.redis.LettuceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final LettuceUtil lettuceUtil;

    @Override
    public void signup(UserVO user) {
        if (userRepository.existsByUserId(user.getUserId())) {
            throw new APIException(APIException.ErrorCode.DUPLICATE_USER);
        }

        userRepository.save(UserEntity.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .nickname(user.getNickname())
                .birthDate(user.getBirthday())
                .password(passwordEncoder.encode(user.getPassword()))
                .state(UserVO.STATE.WAIT.getName())
                .role(UserVO.ROLE.USER.getRole())
                .build());
    }

    @Override
    public UserVO login(UserVO user) {
        UserEntity userEntity = userRepository.findByUserId(user.getUserId())
                .orElseThrow(() -> new APIException(APIException.ErrorCode.USER_INFO_INVALID));

        if (!passwordEncoder.matches(user.getPassword(), userEntity.getPassword())) {
            throw new APIException(APIException.ErrorCode.INVALID_PASSWORD);
        }

        if (!userEntity.isApproved()){
            throw new APIException(APIException.ErrorCode.USER_NOT_APPROVED);
        }

        user = UserVO.toUserVO(userEntity);

        user.setToken(jwtTokenProvider.generateToken(userEntity.getUserId(), userEntity.getRole()));

        lettuceUtil.saveJWT(user.getToken(), user);

        return user;
    }

    @Override
    public List<UserVO> getAllUsers() {
        List<UserEntity> userList = userRepository.findAllByOrderByCreatedAtDesc();

        return userList.stream().map(UserVO::toUserVO).toList();
    }

    @Override
    public UserVO userReissue(UserVO user) {
        Map<String, String> oldTokens = user.getToken();
        String refreshToken = oldTokens.get(ObjectConstant.REFRESH_TOKEN);

        String userId = lettuceUtil.getRefreshToken(refreshToken);

        if (!userId.equals(user.getUserId())) {
            throw new APIException(APIException.ErrorCode.INVALID_TOKEN);
        }

        UserEntity userEntity = userRepository.findByUserId(user.getUserId())
                .orElseThrow(() -> new APIException(APIException.ErrorCode.USER_INFO_INVALID));

        user = UserVO.toUserVO(userEntity);
        String newAccessTokens = jwtTokenProvider.generateAccessToken(userEntity.getUserId(), userEntity.getRole());

        user.setToken(Map.of(
                ObjectConstant.ACCESS_TOKEN, newAccessTokens,
                ObjectConstant.REFRESH_TOKEN, refreshToken
        ));

        lettuceUtil.saveAccessToken(newAccessTokens, user);
        lettuceUtil.blockToken(oldTokens.get(ObjectConstant.ACCESS_TOKEN));

        return user;
    }
}
