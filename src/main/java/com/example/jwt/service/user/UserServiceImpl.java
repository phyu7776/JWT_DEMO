package com.example.jwt.service.user;

import com.example.jwt.config.constant.ObjectConstant;
import com.example.jwt.config.excetion.APIException;
import com.example.jwt.config.jwt.JwtTokenProvider;
import com.example.jwt.utils.LettuceUtil;
import com.example.jwt.utils.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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
            throw new APIException(APIException.ErrorCode.USER_INFO_INVALID);
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
    public Map<String, Object> getAllUsers() {
        List<UserEntity> userList = userRepository.findAllByOrderByCreatedAtDesc();

        List<UserVO> users = userList.stream().map(UserVO::toUserVO).toList();

        return Map.of(
                "users", users,
                "totalCount", users.size()
        );
    }

    @Override
    public UserVO userReissue(UserVO user) {
        Map<String, String> oldTokens = user.getToken();
        String refreshToken = oldTokens.get(ObjectConstant.REFRESH_TOKEN);

        String userId = lettuceUtil.getRefreshToken(refreshToken).toString();

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

    @Override
    public void deleteUser(List<UserVO> user) {
        for (UserVO userVO : user) {
            userRepository.deleteById(userVO.getUID());
        }
    }

    @Override
    @Transactional
    public UserVO updateUser(UserVO user) {

        UserVO requestUser = SecurityUtil.getCurrentUser();

        if (!ObjectUtils.isEmpty(requestUser) && !UserVO.ROLE.ADMIN.getRole().equals(requestUser.getRole())
                && !requestUser.getUID().equals(user.getUID())) {
            throw new APIException(APIException.ErrorCode.USER_INFO_INVALID);
        }

        UserEntity userEntity = userRepository.findById(user.getUID())
                .orElseThrow(() -> new APIException(APIException.ErrorCode.USER_INFO_INVALID));

        if (!ObjectUtils.isEmpty(user.getName()) && !userEntity.getName().equals(user.getName())) {
            userEntity.setName(user.getName());
        }

        if (!ObjectUtils.isEmpty(user.getNickname()) && !userEntity.getNickname().equals(user.getNickname())) {
            userEntity.setNickname(user.getNickname());
        }

        if (user.getBirthday() != null && !userEntity.getBirthDate().equals(user.getBirthday())) {
            userEntity.setBirthDate(user.getBirthday());
        }

        if (!ObjectUtils.isEmpty(user.getPassword()) && !passwordEncoder.matches(user.getPassword(), userEntity.getPassword())) {
            userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        if (!ObjectUtils.isEmpty(user.getRole()) && !userEntity.getRole().equals(user.getRole())) {
            userEntity.setRole(user.getRole());
        }

        if (!ObjectUtils.isEmpty(user.getState()) && !userEntity.getState().equals(user.getState())) {
            userEntity.setState(user.getState());
        }

        return UserVO.toUserVO(userRepository.save(userEntity));
    }

    @Override
    public void changePassword(Map<String, String> passwordInfo) {

        UserVO user = SecurityUtil.getCurrentUser();

        UserEntity entity = userRepository.findById(user.getUID()).orElseThrow(
                () -> new APIException(APIException.ErrorCode.USER_INFO_INVALID));

        String originPassword = passwordInfo.get("currentPassword");

        if (passwordEncoder.matches(originPassword, entity.getPassword())) {
            entity.setPassword(passwordEncoder.encode(passwordInfo.get("newPassword")));
            userRepository.save(entity);
        } else {
            throw new APIException(APIException.ErrorCode.INVALID_PASSWORD);
        }
    }
}
